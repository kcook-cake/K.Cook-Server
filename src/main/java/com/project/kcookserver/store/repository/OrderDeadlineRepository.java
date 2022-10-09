package com.project.kcookserver.store.repository;

import com.project.kcookserver.store.entity.OrderDeadline;
import com.project.kcookserver.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDeadlineRepository extends JpaRepository<OrderDeadline, Long> {

	OrderDeadline findByStore(Store store);
}
