package com.project.kcookserver.account;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmailAndStatus(String email, Status status);
}
