package com.project.kcookserver.orders.entity;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.orders.PaymentType;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @Enumerated(EnumType.STRING)
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

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private LocalDateTime pickUpAt;

    @OneToMany(mappedBy = "orders", cascade = ALL)
    private List<OrdersOptionsRelation> ordersOptionsRelationList = new ArrayList<>();

    public Orders(Account account, Product product, PaymentType paymentType, LocalDateTime pickUpAt, List<Options> optionsList) {

        this.status = RESERVATION;
        this.account = account;
        this.store = product.getStore();
        this.product = product;
        this.price = product.getPrice() - product.getSalePrice();
        this.paymentType = paymentType;
        this.pickUpAt = pickUpAt;
        int index = 1;
        for (Options options : optionsList) {
            price += options.getAdditionalCost();
            this.ordersOptionsRelationList.add(new OrdersOptionsRelation(this, options, index++));
        }
    }

}
