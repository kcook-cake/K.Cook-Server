package com.project.kcookserver.store;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.store.dto.StoreDetailRes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT s FROM Store s JOIN FETCH s.account a " +
            "WHERE a = :account AND s.status = :status")
    Optional<StoreDetailRes> getStoreByAccountAndStatus(@Param(value = "account") Account account, @Param(value = "status") Status status);

    Optional<Store> findByAccountAndStatus(Account account, Status status);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update Store s set s.representativeStore = false")
    void updateRepresentativeStoreIsNone();

    @Modifying(clearAutomatically = true)
    @Query(value = "Update Store s set s.representativeStore = true WHERE s.storeId in :ids")
    void registerRepresentativeStoreByIds(List<Long> ids);
}
