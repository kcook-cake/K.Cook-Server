package com.project.kcookserver.product.service;

import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.product.dto.OptionsListRes;
import com.project.kcookserver.product.dto.ProductDetailRes;
import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.repository.OptionsRepository;
import com.project.kcookserver.product.repository.ProductQueryRepository;
import com.project.kcookserver.product.repository.ProductRepository;
import com.project.kcookserver.product.repository.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.kcookserver.configure.entity.Status.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OptionsRepository optionsRepository;
    private final ProductRepositoryCustom productRepositoryCustom;

    public Page<ProductListRes> getCakeList(int page, int size, String sortBy, boolean isAsc, String event, String options, Integer lowPrice, Integer highPrice, String area) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> cakeList = productRepositoryCustom.findAllCakeProduct(pageable, event, options, lowPrice, highPrice,area);
        return cakeList.map(ProductListRes::new);
    }

    public ProductDetailRes getDetailProduct(Long productId) {
        Product product = productRepository.findByProductIdAndStatus(productId, VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
        List<OptionsListRes> optionsList = optionsRepository.findAllByProductId(productId)
                .stream().map(OptionsListRes::new).collect(Collectors.toList());
        ProductDetailRes productDetailRes = new ProductDetailRes(product, optionsList);
        return productDetailRes;
    }
}
