package com.project.kcookserver.product;

import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.review.Review;
import com.project.kcookserver.store.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String name;

    private String thumbnail;

    private Integer price;

    private Integer salePrice;

    private Float raiting;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    private Boolean isCake;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

}
