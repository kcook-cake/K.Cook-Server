package com.project.kcookserver.review;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.orders.Orders;
import com.project.kcookserver.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ordersId")
    private Orders orders;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    private String image1;
    private String image2;
    private String image3;
    private String image4;

    private String contents;

    private Integer raiting;

}
