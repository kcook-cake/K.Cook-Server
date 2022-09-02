package com.project.kcookserver.product.dto;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListRes {

    private Long productId;

    private Status status;

    private Boolean isCake;

    private String image;

    private String raiting;

    private String storeName;

    private String name;

    private Integer price;

    private Integer salePrice;

    private Integer resultPrice;

    private Long reviewCount;

    public ProductListRes(Product product) {
        this.productId = product.getProductId();
        this.status = product.getStatus();
        this.isCake = product.getIsCake();
        this.image = product.getImage();
        this.raiting = new DecimalFormat("#.00").format(product.getRaiting());
        // this.storeName = product.getStore().getName();
        this.name = product.getName();
        this.price = product.getPrice();
        this.salePrice = product.getSalePrice();
        this.resultPrice = price - salePrice;
        this.reviewCount = product.getReviewCount();
    }

    @QueryProjection
    public ProductListRes(String name, int price, String storeName, String image) {
        this.name = name;
        this.price = price;
        this.storeName = storeName;
        this.image = image;
    }
}
