package com.project.kcookserver.product.controller;

import com.project.kcookserver.configure.response.CommonResponse;
import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.product.dto.ProductDetailRes;
import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.dto.UpdatePopularityReq;
import com.project.kcookserver.product.dto.UpdateRepresentativeCakeReq;
import com.project.kcookserver.product.service.ProductService;
import com.project.kcookserver.product.vo.PopularProduct;
import com.project.kcookserver.store.enums.Area;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
            @RequestParam(name = "area", required = false) Area area
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
            @RequestParam(name = "area", required = false) Area area
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
    @PostMapping(value = "/products")
    public DataResponse<Long> createProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestBody CreateProductReq createProductReq) {
        Long productId = productService.createProduct(customUserDetails ,createProductReq);
        return responseService.getDataResponse(productId);
    }

    @Operation(summary = "상품 이미지 추가 API", description = "운영자 , 사업자 계정만 사용 가능")
    @PatchMapping(value = "/products/{productId}/photos")
    public CommonResponse addProductImages(@PathVariable Long productId,
        @RequestPart MultipartFile productImage1, @RequestPart MultipartFile productImage2, @RequestPart MultipartFile productImage3,
        @RequestPart MultipartFile productImage4, @RequestPart MultipartFile productImage5,
        @RequestPart MultipartFile optionImage1,  @RequestPart MultipartFile optionImage2,  @RequestPart MultipartFile optionImage3){
        productService.addProductImages(productId ,productImage1, productImage2, productImage3, productImage4, productImage5,
            optionImage1, optionImage2, optionImage3);
        return responseService.getSuccessResponse();
    }

    @Operation(summary = "상품 이미지 추가 API", description = "상품 ID를 기준으로 이미지 추가")
    @PatchMapping(value = "/products/image")
    public CommonResponse uploadProductImage(@RequestParam("images") MultipartFile multipartFile,
        @RequestParam("productId") Long productId) throws IOException {
        productService.uploadProductImage(multipartFile, productId);
        return responseService.getSuccessResponse();
    }

    @Operation(summary = "상품 인기순 변경 API")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @PatchMapping(value = "/products/popularity")
    public CommonResponse updatePopularity(@RequestBody UpdatePopularityReq updatePopularityReq) {
        productService.updatePopularity(updatePopularityReq.getPopularities());
        return responseService.getSuccessResponse();
    }

    @Operation(summary = "상품 인기순 조회 API")
    @GetMapping(value = "/popular-products")
    public DataResponse<Page<PopularProduct>> getPopularProducts(
        @RequestParam Integer page
    ) {
        if (page == null) page = 1;
        page = page - 1;

        Page<PopularProduct> popularProducts = productService.getPopularProducts(page);
        return responseService.getDataResponse(popularProducts);
    }

    @GetMapping(value = "/products/update")
    public DataResponse<Page<ProductListRes>> getProductsByUpdatedAtDesc(
        @RequestParam Integer page
    ) {
        if (page == null) page = 1;
        page = page - 1;

        Page<ProductListRes> productsRes = productService.getProductsByUpdatedAtDesc(page);
        return responseService.getDataResponse(productsRes);
    }

    @Operation(summary = "대표 케익 조회 API")
    @GetMapping(value = "products/representative-cake")
    public DataResponse<List<ProductListRes>> getRepresentativeCakes() {
        List<ProductListRes> representativeCakes = productService.getRepresentativeCakes();
        return responseService.getDataResponse(representativeCakes);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-ACCESS-TOKEN", value = "로그인 성공 후 토큰", dataTypeClass = String.class, paramType = "header")
    })
    @Operation(summary = "대표 케익 수정 API")
    @PostMapping(value = "/products/representative-cake")
    public CommonResponse updateRepresentativeCake(@RequestBody UpdateRepresentativeCakeReq updateRepresentativeCakeReq) {
        productService.updateRepresentativeCake(updateRepresentativeCakeReq.getCakeIds());
        return responseService.getSuccessResponse();
    }
}
