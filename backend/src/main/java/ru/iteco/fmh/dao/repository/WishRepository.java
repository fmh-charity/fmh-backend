package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.wish.Wish;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer> {

    @Query(value = "SELECT w from Wish w inner join w.wishRoles wr where w.deleted is false " +
            "and w.status in :status and wr.name in :roleNames")
    Page<Wish> findAllByCurrentRole(
            @Param("status") List<Status> status,
            @Param("roleNames") List<String> roleNames,
            Pageable pageable);

    @PostFilter("@roleMatchesService.findMatchesByRoleList(filterObject.wishRoles, authentication)")
    List<Wish> findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Collection<Status> status);

    @PostFilter("@roleMatchesService.findMatchesByRoleList(filterObject.wishRoles, authentication)")
    List<Wish> findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(
            Integer patientId, Collection<Status> status
    );

    @PostFilter("@roleMatchesService.findMatchesByRoleList(filterObject.wishRoles, authentication)")
    List<Wish> findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Integer patientId);

    @PostAuthorize("@roleMatchesService.findMatchesByRoleList(returnObject.wishRoles, authentication)")
    Wish findWishById(int id);

    @NonNull
    @PostAuthorize("@roleMatchesService.findMatchesByRoleList(returnObject.get().wishRoles, authentication)")
    Optional<Wish> findById(Integer id);
}
