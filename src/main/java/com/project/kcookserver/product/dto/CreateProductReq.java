package com.project.kcookserver.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductReq {

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer salePrice;

    @NotNull
    private Boolean isCake;

    private Boolean isTodayCake;

    private Integer maxOfToday;

    private Integer todaySaleNumber;

    private Boolean isOriginShow;

    private Boolean isTodayShow;

    @Nullable
    private List<CreateOptionReq> newOptionsList;

}
