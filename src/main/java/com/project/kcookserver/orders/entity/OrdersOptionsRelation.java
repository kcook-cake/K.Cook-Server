package com.project.kcookserver.orders.entity;

import com.project.kcookserver.configure.entity.BaseTimeEntity;
import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;
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
public class OrdersOptionsRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ordersId")
    private Orders orders;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "optionsId")
    private Options options;

    private Integer optionsNumber;

    public OrdersOptionsRelation(Orders orders, Options options, Integer optionsNumber) {
        this.status = VALID;
        this.orders = orders;
        this.options = options;
        this.optionsNumber = optionsNumber;
    }

}
