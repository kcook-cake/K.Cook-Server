package com.project.kcookserver.review.controller;

import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.review.dto.CreateReviewDto;
import com.project.kcookserver.review.dto.ReviewKeywordWithKorean;
import com.project.kcookserver.review.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Review API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class ReviewController {

	private final ReviewService reviewService;
	private final ResponseService responseService;

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
	})
	@Operation(summary = "리뷰 등록 API")
	@PostMapping(value = "/review")
	public CommonResponse createReview(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CreateReviewDto createReviewDto) {
		reviewService.createReview(customUserDetails.getAccount(), createReviewDto);
		return responseService.getSuccessResponse();
	}

	@Operation(summary = "리뷰 키워드 반환 API")
	@GetMapping(value = "/review/keywords")
	public DataResponse<List<ReviewKeywordWithKorean>> getReviewKeywords() {
		List<ReviewKeywordWithKorean> reviewKeywords = reviewService.getReviewKeywords();
		return responseService.getDataResponse(reviewKeywords);
	}

}
