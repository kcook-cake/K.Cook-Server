package com.project.kcookserver.product;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.ProductListRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.reviews LEFT JOIN FETCH p.store WHERE p.status = :status and p.isCake = :isCake",
            countQuery = "SELECT p FROM Product p WHERE p.status = :status and p.isCake = :isCake")
    Page<ProductListRes> findAllListResByStatusAndIsCake(@Param(value = "status") Status status, @Param(value = "isCake") Boolean isCake, Pageable pageable);

}
