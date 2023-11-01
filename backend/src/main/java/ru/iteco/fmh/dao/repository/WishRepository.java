package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.wish.Status;
import ru.iteco.fmh.model.wish.Wish;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer> {

    @Query(nativeQuery = true, value =
            """
                    select distinct w.* from wish w
                             join wish_visibility wv on w.id = wv.wish_id
                             join roles r on wv.role_id = r.id
                             left join wish_executors we on w.id = we.wish_id
                             left join patient p on p.id = w.patient_id
                             left join profile pp on pp.id = p.profile_id
                             left join users uc on uc.id = w.creator_id
                             left join users ue on ue.id = we.executor_id
                             left join profile pe on ue.profile_id = pe.id
                    where w.deleted = false
                      and (r.name in :roleNames or uc.login = :creatorLogin)
                      and ((lower(w.status) like lower(concat('%', :searchValue, '%')))
                        or (
                               (
                                           lower(pp.first_name) like lower(concat('%', :searchValue, '%'))
                                       and coalesce(lower(pp.first_name), '') like lower(concat('%', :searchValue, '%'))
                                   )
                               )
                        or (
                               (
                                           lower(pp.last_name) like lower(concat('%', :searchValue, '%'))
                                       and coalesce(lower(pp.last_name), '') like lower(concat('%', :searchValue, '%'))
                                   )
                               )
                        or (
                               (
                                           lower(pp.middle_name) like lower(concat('%', :searchValue, '%'))
                                       and coalesce(lower(pp.middle_name), '') like lower(concat('%', :searchValue, '%'))
                                   )
                               )
                        or (
                               (
                                           lower(pe.first_name) like lower(concat('%', :searchValue, '%'))
                                       and coalesce(lower(pe.first_name), '') like lower(concat('%', :searchValue, '%'))
                                   )
                               )
                        or (
                               (
                                           lower(pe.last_name) like lower(concat('%', :searchValue, '%'))
                                       and coalesce(lower(pe.last_name), '') like lower(concat('%', :searchValue, '%'))
                                   )
                               )
                        or (
                               (
                                           lower(pe.middle_name) like lower(concat('%', :searchValue, '%'))
                                       and coalesce(lower(pe.middle_name), '') like lower(concat('%', :searchValue, '%'))
                                   )
                               )
                        )"""
    )
    Page<Wish> findAllBySearchValue(
            @Param("roleNames") List<String> roleNames,
            @Param("creatorLogin") String creatorLogin,
            @Param("searchValue") String searchValue,
            Pageable pageable);

    /*@Query(value =
            "SELECT DISTINCT w FROM Wish w "
                    + "WHERE w.id IN (SELECT w1.id from Wish w1 "
                    + "INNER JOIN w1.wishRoles wr "
                    + "WHERE w1.deleted is false "
                    + "AND (wr.name IN :roleNames OR w1.creator.login = :creatorLogin) "
                    + "AND (SELECT COUNT(we) FROM w1.executors we) = 0 "
                    + "AND (lower(w1.status) LIKE lower(concat('%', :searchValue, '%')) "
                    + "    OR lower(w1.patient.profile.firstName) LIKE lower(concat('%', :searchValue, '%')) "
                    + "    OR lower(w1.patient.profile.lastName) LIKE lower(concat('%', :searchValue, '%')) "
                    + "    OR lower(w1.patient.profile.middleName) LIKE lower(concat('%', :searchValue, '%')))"
                    + ") "
                    + "OR w.id IN (SELECT w2.id from Wish w2 "
                    + "inner join w2.wishRoles wr "
                    + "left JOIN w2.executors we "
                    + "where w2.deleted is false "
                    + "    and (wr.name in :roleNames or w2.creator.login = :creatorLogin) "
                    + "    and lower(w2.status) like lower(concat('%', :searchValue, '%')) "
                    + "        or lower(w2.patient.profile.firstName) like lower(concat('%', :searchValue, '%')) "
                    + "        or lower(w2.patient.profile.lastName) like lower(concat('%', :searchValue, '%')) "
                    + "        or lower(w2.patient.profile.middleName) like lower(concat('%', :searchValue, '%')) "
                    + "        or lower(we.executor.profile.firstName) like lower(concat('%', :searchValue, '%'))"
                    + "        or lower(we.executor.profile.lastName) like lower(concat('%', :searchValue, '%'))"
                    + "        or lower(we.executor.profile.middleName) like lower(concat('%', :searchValue, '%'))"
                    + ")"
    )
    List<Wish> findAllBySearchValue(
            @Param("roleNames") List<String> roleNames,
            @Param("creatorLogin") String creatorLogin,
            @Param("searchValue") String searchValue,
            PageRequest pageRequest);*/

    @PostFilter("@roleMatchesService.findMatchesByRoleList(filterObject.wishRoles, authentication)"
            + "|| authentication.name.equals(filterObject.creator.login)")
    List<Wish> findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Collection<Status> status);

    @PostFilter("@roleMatchesService.findMatchesByRoleList(filterObject.wishRoles, authentication) "
            + "|| authentication.name.equals(filterObject.creator.login)")
    List<Wish> findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(
            Integer patientId, Collection<Status> status
    );

    @PostFilter("@roleMatchesService.findMatchesByRoleList(filterObject.wishRoles, authentication)"
            + "|| authentication.name.equals(filterObject.creator.login)")
    List<Wish> findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Integer patientId);

    @PostAuthorize("@roleMatchesService.findMatchesByRoleList(returnObject.wishRoles, authentication)"
            + "|| authentication.name.equals(returnObject.creator.login)")
    Wish findWishById(int id);

    @NonNull
    @PostAuthorize("@roleMatchesService.findRoleMatchesByWishOptional(returnObject, authentication)"
            + "|| authentication.name.equals(returnObject.ifPresentOrElse(returnObject.get().creator.login, true))")
    Optional<Wish> findById(Integer id);
}
