package com.project.kcookserver.product.repository;

import com.project.kcookserver.product.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionsRepository extends JpaRepository<Options, Long> {

    @Query(
            value = "SELECT * FROM Options o " +
                    "WHERE (o.optionsId IN (SELECT r.optionsId FROM ProductOptionsRelation r WHERE r.productId = :productId) AND o.status = 'VALID')",
            nativeQuery = true
    )
    List<Options> findAllByProductId(@Param(value = "productId") Long productId);

}
