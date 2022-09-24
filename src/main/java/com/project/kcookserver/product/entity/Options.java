package com.project.kcookserver.product.entity;

import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.CreateOptionReq;
import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.FetchType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Options extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionsId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private OptionsCategoryType category;

    private String contents;

    private Integer additionalCost;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Size(max = 500)
    private String imageUrl;

    private Long itemNumber;

    private String itemType;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Options(CreateOptionReq createOptionReq) {
        this.status = VALID;
        this.category = createOptionReq.getCategory();
        this.contents = createOptionReq.getContents();
        this.additionalCost = createOptionReq.getAdditionalCost();
        if (this.category.equals(OptionsCategoryType.IMAGE))
            this.imageUrl = createOptionReq.getImageUrl();
        this.itemNumber = createOptionReq.getItemNumber();
        this.itemType = createOptionReq.getItemType();
    }

}
