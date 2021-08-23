package com.project.kcookserver.product;

import com.project.kcookserver.configure.response.DataResponse;
import com.project.kcookserver.configure.response.ResponseService;
import com.project.kcookserver.product.dto.ProductListRes;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(name = "isAsc", required = false) Boolean isAsc
    ){
        if (page == null) page = 1;
        page = page - 1;
        if (size == null) size = 10;
        if (isAsc == null) isAsc = true;
        if (sortBy == null) sortBy = "updatedAt";
        Page<ProductListRes> result =  productService.getProductList(page, size, sortBy, isAsc, true);
        return responseService.getDataResponse(result);
    }

    @Operation(summary = "추가 상품 목록 조회 API", description = "page, size, sortBy, isAsc RequestParam 설정")
    @GetMapping(value = "/additional-products")
    public DataResponse<Page<ProductListRes>> getAdditionalProductList(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "isAsc", required = false) Boolean isAsc
    ){
        if (page == null) page = 1;
        page = page - 1;
        if (size == null) size = 10;
        if (isAsc == null) isAsc = true;
        if (sortBy == null) sortBy = "updatedAt";
        Page<ProductListRes> result =  productService.getProductList(page, size, sortBy, isAsc, false);
        return responseService.getDataResponse(result);
    }

}
