package com.project.kcookserver.product.dto;

import lombok.Data;

@Data
public class Popularity {

	private long cakeId;
	private int popularityRank; // 인기 순위
}
