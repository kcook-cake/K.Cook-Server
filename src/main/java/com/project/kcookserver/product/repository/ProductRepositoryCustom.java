package com.project.kcookserver.product.repository;

import com.project.kcookserver.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> findAllCakeProduct(Pageable pageable, String event, String options, Integer lowPrice, Integer highPrice, String area);
}
