package com.project.kcookserver.product.dto;

import java.util.List;

import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CreateOptionReq {

    @NotNull
    private OptionsCategoryType category;

    private String contents;

    private Integer additionalCost;

    @Size(max = 500)
    private String imageUrl;

    private Long itemNumber;

    private String itemType;

    private List<CreateChildOptionReq> child;

    private String categoryTitle;

}
