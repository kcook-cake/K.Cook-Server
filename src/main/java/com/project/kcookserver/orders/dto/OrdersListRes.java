package com.project.kcookserver.orders.dto;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.orders.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrdersListRes {

    private Long ordersId;

    private Status status;

    private Long productId;

    private String productName;

    private Long storeId;

    private String storeName;

    private List<OptionsListRes> optionsList;

    private LocalDateTime createdAt;

    private LocalDateTime pickUpAt;

    private Integer price;

    public OrdersListRes(Orders orders) {
        this.ordersId = orders.getOrdersId();
        this.status = orders.getStatus();
        this.productId = orders.getProduct().getProductId();
        this.productName = orders.getProduct().getName();
        this.storeId = orders.getStore().getStoreId();
        this.storeName = orders.getStore().getName();
        this.optionsList = orders.getOrdersOptionsRelationList().stream().map(OptionsListRes::new).collect(Collectors.toList());
        this.createdAt = orders.getCreatedAt();
        this.pickUpAt = orders.getPickUpAt();
        this.price = orders.getPrice();
    }


}
