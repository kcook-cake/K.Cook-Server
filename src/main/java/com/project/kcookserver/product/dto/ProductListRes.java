package com.project.kcookserver.product.dto;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListRes {

    private Long productId;

    private Status status;

    private Boolean isCake;

    private String thumbnail;

    private String raiting;

    private String storeName;

    private String name;

    private Integer price;

    private Integer salePrice;

    private Integer resultPrice;

    private Integer reviewCount;

    public ProductListRes(Product product) {
        this.productId = product.getProductId();
        this.status = product.getStatus();
        this.isCake = product.getIsCake();
        this.thumbnail = product.getThumbnail();
        this.raiting = new DecimalFormat("#.00").format(product.getRaiting());
        this.storeName = product.getStore().getName();
        this.name = product.getName();
        this.price = product.getPrice();
        this.salePrice = product.getSalePrice();
        this.resultPrice = price - salePrice;
        this.reviewCount = product.getReviews().size();
    }

}
