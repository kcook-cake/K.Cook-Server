package com.project.kcookserver.product.entity;

import static com.project.kcookserver.configure.entity.Status.VALID;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.review.Review;
import com.project.kcookserver.store.Store;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Integer popularityRank;

    private boolean representativeCake;

    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "storeId")
    private Store store;

    private Boolean isCake;

    private Integer salesRate;

    private Long reviewCount;


    @OneToMany(mappedBy = "product", cascade = ALL)
    private List<Review> reviews = new ArrayList<>();

    public Product(CreateProductReq createProductReq) {
        this.status = VALID;
        this.name = createProductReq.getName();
        this.price = createProductReq.getPrice();
        this.salePrice = createProductReq.getSalePrice();
        // this.store = account.getStore();
        this.isCake = createProductReq.getIsCake();
        this.reviewCount = 0L;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void changePopularityRank(int popularityRank) {
        this.popularityRank = popularityRank;
    }

    public void deletePopularityRank() {
        this.popularityRank = null;
    }
}
