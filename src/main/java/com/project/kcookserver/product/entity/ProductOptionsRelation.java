package com.project.kcookserver.product.entity;

import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.project.kcookserver.configure.entity.Status.*;
import static javax.persistence.FetchType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductOptionsRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "optionsId")
    private Options options;

    public ProductOptionsRelation(Product product, Options options) {
        this.status = VALID;
        this.product = product;
        this.options = options;
    }

}
