package com.project.kcookserver.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

import com.project.kcookserver.product.entity.Product;

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

    @Nullable
    private List<CreateOptionReq> newOptionsList;

}
