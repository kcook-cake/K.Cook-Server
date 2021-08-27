package com.project.kcookserver.product.service;

import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.kcookserver.configure.entity.Status.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductListRes> getProductList(int page, int size, String sortBy, boolean isAsc, boolean isCake) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllListResByStatusAndIsCake(VALID, isCake, pageable);
    }
}
