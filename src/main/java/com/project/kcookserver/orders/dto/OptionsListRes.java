package com.project.kcookserver.orders.dto;

import com.project.kcookserver.orders.entity.OrdersOptionsRelation;
import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OptionsListRes {

    private Integer optionsNumber;
    private OptionsCategoryType category;
    private String contents;

    public OptionsListRes(OrdersOptionsRelation ordersOptionsRelation) {
        this.optionsNumber = ordersOptionsRelation.getOptionsNumber();
        this.category = ordersOptionsRelation.getOptions().getCategory();
        this.contents = ordersOptionsRelation.getOptions().getContents();
    }

}
