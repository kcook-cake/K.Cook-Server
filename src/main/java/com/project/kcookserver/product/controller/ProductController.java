package com.project.kcookserver.product.controller;

import java.io.IOException;

import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.product.dto.ProductDetailRes;
import com.project.kcookserver.product.service.ProductService;
import com.project.kcookserver.product.dto.ProductListRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"Product API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/app")
public class ProductController {

    private final ProductService productService;
    private final ResponseService responseService;

    @Operation(summary = "케이크 상품 목록 조회 API", description = "page, size, sortBy, isAsc RequestParam 설정")
    @GetMapping(value = "/cakes")
    public DataResponse<Page<ProductListRes>> getCakeList(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "isAsc", required = false) Boolean isAsc,
            @RequestParam(name = "event", required = false) String event,
            @RequestParam(name = "options", required = false) String options,
            @RequestParam(name = "lowPrice", required = false) Integer lowPrice,
            @RequestParam(name = "highPrice", required = false) Integer highPrice,
            @RequestParam(name = "area", required = false) String area
    ){
        if (page == null) page = 1;
        page = page - 1;
        if (size == null) size = 10;
        if (isAsc == null) isAsc = true;
        if (sortBy == null) sortBy = "updatedAt";
        Page<ProductListRes> result =  productService.getCakeList(page, size, sortBy, isAsc, event, options, lowPrice, highPrice, area);
        return responseService.getDataResponse(result);
    }

    @Operation(summary = "추가 상품 목록 조회 API", description = "page, size, sortBy, isAsc RequestParam 설정")
    @GetMapping(value = "/additional-products")
    public DataResponse<Page<ProductListRes>> getAdditionalProductList(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "isAsc", required = false) Boolean isAsc,
            @RequestParam(name = "options", required = false) String options,
            @RequestParam(name = "lowPrice", required = false) Integer lowPrice,
            @RequestParam(name = "highPrice", required = false) Integer highPrice,
            @RequestParam(name = "area", required = false) String area
    ){
        if (page == null) page = 1;
        page = page - 1;
        if (size == null) size = 10;
        if (isAsc == null) isAsc = true;
        if (sortBy == null) sortBy = "updatedAt";
        Page<ProductListRes> result =  productService.getAdditionalProductsList(page, size, sortBy, isAsc, options, lowPrice, highPrice, area);
        return responseService.getDataResponse(result);
    }

    @Operation(summary = "하나의 상품 내용 조회 API", description = "상품 번호가 PathVariable로 들어감")
    @GetMapping(value = "/products/{productId}")
    public DataResponse<ProductDetailRes> getDetailProduct(@PathVariable(value = "productId") Long productId) {
        ProductDetailRes productDetailRes = productService.getDetailProduct(productId);
        return responseService.getDataResponse(productDetailRes);
    }

    @Operation(summary = "상품 생성 API", description = "운영자 , 사업자 계정만 사용 가능")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @PostMapping(value = "/products")
    public DataResponse<Long> createProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestBody CreateProductReq createProductReq) {
        Long productId = productService.createProduct(customUserDetails ,createProductReq);
        return responseService.getDataResponse(productId);
    }

    @Operation(summary = "상품 이미지 추가 API", description = "상품 ID를 기준으로 이미지 추가")
    @PatchMapping(value = "/products/image")
    public CommonResponse uploadProductImage(@RequestParam("images") MultipartFile multipartFile,
        @RequestParam("productId") Long productId) throws IOException {
        productService.uploadProductImage(multipartFile, productId);
        return responseService.getSuccessResponse();
    }

}
