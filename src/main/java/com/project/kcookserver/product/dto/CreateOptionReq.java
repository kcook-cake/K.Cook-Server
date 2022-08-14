package com.project.kcookserver.product.dto;

import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CreateOptionReq {

    @NotNull
    private OptionsCategoryType category;

    @NotNull
    private String contents;

    @NotNull
    private Integer additionalCost;

    private Product product;

}
