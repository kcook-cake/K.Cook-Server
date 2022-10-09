package com.project.kcookserver.store.controller;

import com.project.kcookserver.configure.aop.annotation.AccountLog;
import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.store.dto.CreateStoreReq;
import com.project.kcookserver.store.dto.StoreDetailRes;
import com.project.kcookserver.store.dto.UpdateDefaultPageStore;
import com.project.kcookserver.store.dto.UpdateOrderDeadlineReq;
import com.project.kcookserver.store.enums.Area;
import com.project.kcookserver.store.service.StoreService;
import com.project.kcookserver.util.ValidationExceptionProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"Store API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app/stores")
public class StoreController {

	private final StoreService storeService;
	private final ResponseService responseService;

	@AccountLog
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
	})
	@Operation(summary = "스토어 조회 API", description = "인증된 Account를 기준으로 스토어 조회")
	@GetMapping("/account/auth")
	public DataResponse<StoreDetailRes> getStoreInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		StoreDetailRes storeDetailRes = storeService.getStoreInfo(customUserDetails);
		return responseService.getDataResponse(storeDetailRes);
	}

	@AccountLog
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
	})
	@Operation(summary = "스토어 생성 API", description = "인증된 Account를 기준으로 스토어 생성")
	@PostMapping("/accounts/auth")
	public DataResponse<Long> createStoreByAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody @Valid CreateStoreReq dto, Errors errors) {
		if (errors.hasErrors()) {
			ValidationExceptionProvider.throwValidError(errors);
		}
		Long storeId = storeService.createStoreByAccount(customUserDetails, dto);
		return responseService.getDataResponse(storeId);
	}

	@AccountLog
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
	})
	@Operation(summary = "스토어 수정 API", description = "인증된 Account를 기준으로 스토어 수정")
	@PutMapping("/accounts/auth")
	public CommonResponse updateStoreByAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody @Valid CreateStoreReq dto, Errors errors) {
		if (errors.hasErrors()) {
			ValidationExceptionProvider.throwValidError(errors);
		}
		storeService.updateStoreByAccount(customUserDetails, dto);
		return responseService.getSuccessResponse();
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
	})
	@Operation(summary = "디폴트 페이지 스토어 수정 API")
	@PostMapping(value = "/default-page-store")
	public CommonResponse updateRepresentativeStore(@RequestBody UpdateDefaultPageStore updateDefaultPageStore) {
		storeService.updateRepresentativeStore(updateDefaultPageStore.getDefaultPageStores());
		return responseService.getSuccessResponse();
	}

	@Operation(summary = "디폴트 페이지 스토어 조회 API")
	@GetMapping(value = "/default-page-store")
	public DataResponse<List<StoreDetailRes>> getRepresentativeStores() {
		List<StoreDetailRes> defaultPageCakes = storeService.getDefaultPageStores();
		return responseService.getDataResponse(defaultPageCakes);
	}

	@Operation(summary = "지역별 스토어 조회 API")
	@GetMapping(value = "/area")
	public DataResponse<Page<StoreDetailRes>> getStoresByArea(
		@RequestParam Area area,
		@RequestParam(required = false) Integer page,
		@RequestParam(required = false) Integer size,
		@RequestParam(name = "sortBy", required = false) String sortBy,
		@RequestParam(name = "isAsc", required = false) Boolean isAsc
	) {
		if (page == null) {
			page = 1;
		}
		page = page - 1;
		if (size == null) {
			size = 10;
		}
		if (isAsc == null) {
			isAsc = true;
		}
		if (sortBy == null) {
			sortBy = "updatedAt";
		}

		Page<StoreDetailRes> storesByArea = storeService.getStoresByArea(area, page, size, isAsc, sortBy);
		return responseService.getDataResponse(storesByArea);
	}

	@Operation(summary = "스토어 대표 사진 변경 API")
	@PostMapping(value = "/images")
	public CommonResponse updateStoreImages(@RequestBody long storeId,
		@RequestPart(required = false) MultipartFile storeImage1,
		@RequestPart(required = false) MultipartFile storeImage2,
		@RequestPart(required = false) MultipartFile storeImage3,
		@RequestPart(required = false) MultipartFile storeImage4,
		@RequestPart(required = false) MultipartFile storeImage5
	) {
		storeService.updateStoreImage(storeId, storeImage1, storeImage2, storeImage3, storeImage4, storeImage5);
		return responseService.getSuccessResponse();
	}

	@Operation(summary = "스토어 별 마감일 설정 API")
	@PostMapping(value = "/deadline")
	public CommonResponse updateOrderDeadline(@RequestBody UpdateOrderDeadlineReq updateOrderDeadlineReq) {
		storeService.updateOrderDeadline(updateOrderDeadlineReq);
		return responseService.getSuccessResponse();
	}
}