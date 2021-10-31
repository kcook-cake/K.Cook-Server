package com.project.kcookserver.configure.repository;

import com.project.kcookserver.configure.entity.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {
}
