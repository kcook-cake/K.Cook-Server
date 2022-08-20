package com.project.kcookserver.product.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PopularProduct {

	private int popularRank;

	private String name;

	private int price;

	private String image;

	private String storeName;

	@QueryProjection
	public PopularProduct(int popularRank, String name, int price, String image, String storeName) {
		this.popularRank = popularRank;
		this.name = name;
		this.price = price;
		this.image = image;
		this.storeName = storeName;
	}
}
