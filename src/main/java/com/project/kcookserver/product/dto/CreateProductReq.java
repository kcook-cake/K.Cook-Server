package com.project.kcookserver.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductReq {

    @NotNull
    private String name;

    @NotNull
    private String thumbnail;

    @NotNull
    private Integer price;

    @NotNull
    private Integer salePrice;

    @NotNull
    private Boolean isCake;

    private List<Long> existOptionsIdList;

    private List<CreateOptionReq> newOptionsList;

}
