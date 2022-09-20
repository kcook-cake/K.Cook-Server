package com.project.kcookserver.product.repository;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OptionsRepository extends JpaRepository<Options, Long> {

    List<Options> findAllByProductAndStatus(Product product, Status status);

    Optional<Options> findByOptionsIdAndStatus(Long optionsId, Status status);

}
