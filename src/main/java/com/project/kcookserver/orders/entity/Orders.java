package com.project.kcookserver.orders.entity;

import static com.project.kcookserver.configure.entity.Status.RESERVATION;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.orders.PaymentType;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.review.entity.Review;
import com.project.kcookserver.store.entity.Store;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

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
