package com.project.kcookserver.product.dto;

import java.util.List;

import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OptionsListRes {

    private Long optionsId;

    private OptionsCategoryType category;

    private String contents;

    private Integer additionalCost;

    private List<ChildOptionsListRes> childOptionsList;

    public OptionsListRes(Options options) {
        this.optionsId = options.getOptionsId();
        this.category = options.getCategory();
        this.contents = options.getContents();
        this.additionalCost = options.getAdditionalCost();
    }

}
