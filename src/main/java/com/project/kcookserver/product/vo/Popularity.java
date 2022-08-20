package com.project.kcookserver.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Popularity {

	private long cakeId;
	private int popularityRank; // 인기 순위
}
