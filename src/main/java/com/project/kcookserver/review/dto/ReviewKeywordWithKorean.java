package com.project.kcookserver.review.dto;

import com.project.kcookserver.review.entity.Review.ReviewKeyword;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewKeywordWithKorean {

	private ReviewKeyword reviewKeyword;

	private String korean;
}
