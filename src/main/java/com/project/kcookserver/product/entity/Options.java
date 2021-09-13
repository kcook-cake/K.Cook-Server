package com.project.kcookserver.product.entity;

import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.CreateOptionReq;
import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.project.kcookserver.configure.entity.Status.*;

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

    public Options(CreateOptionReq createOptionReq) {
        this.status = VALID;
        this.category = createOptionReq.getCategory();
        this.contents = createOptionReq.getContents();
        this.additionalCost = createOptionReq.getAdditionalCost();
    }

}
