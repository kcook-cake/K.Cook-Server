package com.project.kcookserver.orders;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.orders.dto.OrdersListRes;
import com.project.kcookserver.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(
            "SELECT o FROM Orders o " +
                    "LEFT JOIN FETCH o.account " +
                    "LEFT JOIN FETCH o.product " +
                    "LEFT JOIN FETCH o.store " +
                    "LEFT JOIN FETCH o.ordersOptionsRelationList oor " +
                    "LEFT JOIN FETCH oor.options " +
                    "WHERE (o.status != 'DELETED' AND o.account = :account)"
    )
    List<OrdersListRes> findAllByAccountAndStatus(@Param(value = "account") Account account);
}
