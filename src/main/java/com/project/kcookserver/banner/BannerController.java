package com.project.kcookserver.banner;

import com.project.kcookserver.banner.dto.RegisterCarouselBannerReq;
import com.project.kcookserver.banner.dto.RegisterStaticBannerReq;
import com.project.kcookserver.banner.dto.registeredBanner;
import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Operation(summary = " 슬라이드 배너 조회")
	@GetMapping("/carousel")
	public DataResponse<List<registeredBanner>> getCarouselBanner() {
		List<registeredBanner> carouselBanners = bannerService.getCarouselBanners();
		return responseService.getDataResponse(carouselBanners);
	}

	@Operation(summary = " 고정 배너 조회")
	@GetMapping("/static")
	public DataResponse<registeredBanner> getStaticBanner() {
		registeredBanner staticBanner = bannerService.getStaticBanner();
		return responseService.getDataResponse(staticBanner);
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
	})
	@Operation(summary = " 슬라이드 배너 등록", description = "배너 1개당 이미지 2개씩 등록(웹, 모바일)")
	@PostMapping("/carousel")
	public CommonResponse registerCarouselBanner(@ModelAttribute RegisterCarouselBannerReq registerCarouselBannerReq) throws Exception {
		bannerService.registerCarouselBanners(registerCarouselBannerReq);
		return responseService.getSuccessResponse();
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
	})
	@Operation(summary = "고정 배너 등록", description = "배너 1개당 이미지 2개씩 등록(웹, 모바일)")
	@PostMapping("/static")
	public CommonResponse registerStaticBanner(@ModelAttribute RegisterStaticBannerReq registerStaticBannerReq) throws Exception {
		bannerService.registerStaticBanner(registerStaticBannerReq);
		return responseService.getSuccessResponse();
	}
}