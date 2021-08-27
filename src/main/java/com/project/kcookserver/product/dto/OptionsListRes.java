package com.project.kcookserver.product.dto;

import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OptionsListRes {

    private Long optionsId;

    private OptionsCategoryType category;

    private String contents;

    private Integer additionalCost;

    public OptionsListRes(Options options) {
        this.optionsId = options.getOptionsId();
        this.category = options.getCategory();
        this.contents = options.getContents();
        this.additionalCost = options.getAdditionalCost();
    }

}
