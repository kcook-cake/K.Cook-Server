package com.project.kcookserver.product.entity;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.review.Review;
import com.project.kcookserver.store.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String name;

    @Size(max = 500)
    private String image;

    private Integer price;

    private Integer salePrice;

    private Float raiting = 0F;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    private Boolean isCake;

    private Integer salesRate;

    private Long reviewCount;


    @OneToMany(mappedBy = "product", cascade = ALL)
    private List<Review> reviews = new ArrayList<>();

    public Product(Account account, CreateProductReq createProductReq) {
        this.status = VALID;
        this.name = createProductReq.getName();
        this.price = createProductReq.getPrice();
        this.salePrice = createProductReq.getSalePrice();
        this.store = account.getStore();
        this.isCake = createProductReq.getIsCake();
        this.reviewCount = 0L;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
