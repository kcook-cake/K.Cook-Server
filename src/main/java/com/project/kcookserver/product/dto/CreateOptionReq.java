package com.project.kcookserver.product.dto;

import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOptionReq {

    @NotNull
    private OptionsCategoryType category;

    @NotNull
    private String contents;

    @NotNull
    private Integer additionalCost;

}
