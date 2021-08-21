package com.project.kcookserver.product;

import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.store.Store;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private Status status;

    private String name;

    private String thumbnail;

    private Integer price;

    private Integer salePrice;

    private Float raiting;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    private Boolean isCake;


}
