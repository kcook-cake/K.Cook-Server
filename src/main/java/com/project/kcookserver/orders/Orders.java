package com.project.kcookserver.orders;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.Product;
import com.project.kcookserver.store.Store;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Status status;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    private Integer price;

    private PaymentType paymentType;

    private LocalDateTime pickUpAt;
}
