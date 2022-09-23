package com.project.kcookserver.orders;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.orders.dto.OrdersListRes;
import com.project.kcookserver.orders.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(
            "SELECT DISTINCT o FROM Orders o " +
                    "INNER JOIN FETCH o.account " +
                    "INNER JOIN FETCH o.product " +
                    "INNER JOIN FETCH o.store " +
                    "LEFT JOIN FETCH o.ordersOptionsRelationList oor " +
                    "LEFT JOIN FETCH oor.options " +
                    "WHERE (o.status != 'DELETED' AND o.account = :account)"
    )
    List<OrdersListRes> findAllByAccount(@Param(value = "account") Account account);
}
