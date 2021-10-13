package com.project.kcookserver.product.service;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.product.dto.OptionsListRes;
import com.project.kcookserver.product.dto.ProductDetailRes;
import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.entity.ProductOptionsRelation;
import com.project.kcookserver.product.repository.OptionsRepository;
import com.project.kcookserver.product.repository.ProductOptionsRelationRepository;
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
    private final ProductOptionsRelationRepository productOptionsRelationRepository;

    public Page<ProductListRes> getCakeList
            (int page, int size, String sortBy, boolean isAsc, String event, String options, Integer lowPrice, Integer highPrice, String area) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> cakeList;
        try {
            cakeList = productRepositoryCustom.findAllCakeProduct(pageable, event, options, lowPrice, highPrice,area);
        } catch (Exception exception) {
            throw new CustomException(CustomExceptionStatus.REQUEST_ERROR);
        }
        return cakeList.map(ProductListRes::new);
    }

    public Page<ProductListRes> getAdditionalProductsList
            (Integer page, Integer size, String sortBy, Boolean isAsc, String options, Integer lowPrice, Integer highPrice, String area) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> additionalProductList = productRepositoryCustom.findAllAdditionalProduct(pageable, options, lowPrice, highPrice,area);
        return additionalProductList.map(ProductListRes::new);
    }

    public ProductDetailRes getDetailProduct(Long productId) {
        Product product = productRepository.findByProductIdAndStatus(productId, VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
        List<OptionsListRes> optionsList = optionsRepository.findAllByProductId(productId)
                .stream().map(OptionsListRes::new).collect(Collectors.toList());
        ProductDetailRes productDetailRes = new ProductDetailRes(product, optionsList);
        return productDetailRes;
    }

    @Transactional
    public Long createProduct(CustomUserDetails customUserDetails, CreateProductReq createProductReq) {
        Account account = customUserDetails.getAccount();
        Product product = new Product(account, createProductReq);
        Product save = productRepository.save(product);
        List<Long> existOptionsIdList = createProductReq.getExistOptionsIdList();
        for (Long optionsId : existOptionsIdList) {
            Options options = optionsRepository.findByOptionsIdAndStatus(optionsId, VALID)
                    .orElseThrow(() -> new CustomException(CustomExceptionStatus.OPTIONS_NOT_FOUND));
            productOptionsRelationRepository.save(new ProductOptionsRelation(save, options));
        }
        List<Options> optionsList = createProductReq.getNewOptionsList().stream().map(Options::new).collect(Collectors.toList());
        for (Options options : optionsList) {
            Options optionsSave = optionsRepository.save(options);
            productOptionsRelationRepository.save(new ProductOptionsRelation(save, optionsSave));
        }
        return save.getProductId();
    }
}
