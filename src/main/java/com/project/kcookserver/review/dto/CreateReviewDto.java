package com.project.kcookserver.review.dto;

import com.project.kcookserver.review.entity.Review.ReviewKeyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateReviewDto {

	private long ordersId;

	private long productId;

	private ReviewKeyword contents;

	private int raiting;
}
