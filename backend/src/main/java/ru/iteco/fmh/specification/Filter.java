package ru.iteco.fmh.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.iteco.fmh.model.user.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static ru.iteco.fmh.model.user.RoleClaimStatus.CONFIRMED;

public class Filter implements Specification<User> {
    private final List<Condition> conditions = new ArrayList<>();
    private final List<Predicate> predicates = new ArrayList<>();

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        if (conditions.isEmpty()) {
            return null;
        }
        List<Predicate> predicates = buildPredicates(root, criteriaBuilder);
        return predicates.size() > 1
                ? criteriaBuilder.and(predicates.toArray(new Predicate[0]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root root, CriteriaBuilder criteriaBuilder) {
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaBuilder)));
        return predicates;
    }

    private Predicate buildPredicate(Condition condition, Root root, CriteriaBuilder criteriaBuilder) {
        return switch (condition.getComparision()) {
            case CONTAINS_IN_PROFILE -> buildContainsProfilePredicate(condition, root, criteriaBuilder);
            case CONTAINS_IN_ROLE_IDS -> buildContainsRoleIdsPredicate(condition, root);
            case CONFIRMED_USER -> buildContainsIsConfirmedTruePredicate(root, criteriaBuilder);
            case NOT_CONFIRMED_USER -> buildContainsIsConfirmedFalsePredicate(root, criteriaBuilder);
        };
    }

    private Predicate buildContainsProfilePredicate(Condition condition, Root root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("firstName")),
                        "%" + condition.getValue().toString().toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("lastName")),
                        "%" + condition.getValue().toString().toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("middleName")),
                        "%" + condition.getValue().toString().toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("email")),
                        "%" + condition.getValue().toString().toLowerCase() + "%"));
    }

    private Predicate buildContainsRoleIdsPredicate(Condition condition, Root root) {
        return root.join("userRoles").get("id").in((List<Integer>) condition.getValue());
    }

    private Predicate buildContainsIsConfirmedTruePredicate(Root root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(root.join("userRoleClaim", JoinType.LEFT).isNull(),
                criteriaBuilder.equal(root.get("userRoleClaim").get("status"), CONFIRMED));
    }

    private Predicate buildContainsIsConfirmedFalsePredicate(Root root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.notEqual(root.get("userRoleClaim").get("status"), CONFIRMED);
    }
}
