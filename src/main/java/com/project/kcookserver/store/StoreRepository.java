package com.project.kcookserver.store;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.store.dto.StoreDetailRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT s FROM Store s JOIN FETCH s.account a " +
            "WHERE a = :account AND s.status = :status")
    Optional<StoreDetailRes> getStoreBy(@Param(value = "account") Account account, @Param(value = "status") Status status);

}
