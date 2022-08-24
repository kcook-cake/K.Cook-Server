package com.project.kcookserver.banner;

import com.project.kcookserver.banner.dto.RegisterCarouselBannerReq;
import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Banner API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app/banner")
public class BannerController {

	private final BannerService bannerService;
	private final ResponseService responseService;

	@Operation(summary = "배너 등록", description = "배너 1개당 이미지 2개씩 등록(웹, 모바일)")
	@PostMapping
	public CommonResponse registerCarouselBanner(@ModelAttribute RegisterCarouselBannerReq registerCarouselBannerReq) throws Exception {
		bannerService.registerBanners(registerCarouselBannerReq);
		return responseService.getSuccessResponse();
	}
}