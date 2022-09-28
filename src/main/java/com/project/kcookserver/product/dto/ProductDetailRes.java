package com.project.kcookserver.product.dto;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductDetailRes {

    private Long productId;

    private Status status;

    private String name;

    private String image;

    private Integer price;

    private Integer salePrice;

    private Long storeId;

    private String storeName;

    private Boolean isTodayCake;

    private Integer maxOfToday;

    private Integer todaySaleNumber;

    private Boolean isOriginShow;

    private Boolean isTodayShow;

    private List<OptionsListRes> optionsList;

    private String productImage1;

    private String productImage2;

    private String productImage3;

    private String productImage4;

    private String productImage5;

    private String optionImage1;

    private String optionImage2;

    private String optionImage3;

    public ProductDetailRes(Product product, List<OptionsListRes> optionsList) {
        this.productId = product.getProductId();
        this.status = product.getStatus();
        this.name = product.getName();
        this.price = product.getPrice();
        this.salePrice = product.getSalePrice();
        this.storeId = product.getStore().getStoreId();
        this.storeName = product.getStore().getName();
        this.optionsList = optionsList;
        this.isTodayCake = product.getIsTodayCake();
        this.maxOfToday = product.getMaxOfToday();
        this.todaySaleNumber = product.getTodaySaleNumber();
        this.isOriginShow = product.getIsOriginShow();
        this.isTodayShow = product.getIsTodayShow();
        this.productImage1 = product.getProductImage1();
        this.productImage2 = product.getProductImage2();
        this.productImage3 = product.getProductImage3();
        this.productImage4 = product.getProductImage4();
        this.productImage5 = product.getProductImage5();
        this.optionImage1 = product.getOptionImage1();
        this.optionImage2 = product.getOptionImage2();
        this.optionImage3 = product.getOptionImage3();
    }
}
