package com.project.kcookserver.product.repository;

import com.project.kcookserver.configure.entity.Status;
import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.reviews LEFT JOIN FETCH p.store WHERE p.status = :status and p.isCake = :isCake",
            countQuery = "SELECT p FROM Product p WHERE p.status = :status and p.isCake = :isCake")
    Page<ProductListRes> findAllListResByStatusAndIsCake(@Param(value = "status") Status status, @Param(value = "isCake") Boolean isCake, Pageable pageable);

    @Query(value = "SELECT p FROM Product  p LEFT JOIN FETCH p.store WHERE p.productId = :productId AND p.status = :status")
    Optional<Product> findByProductIdAndStatus(@Param(value = "productId") Long productId, @Param(value = "status") Status status);

    List<Product> findByPopularityRankIsNotNull();

    List<Product> findByProductIdIn(List<Long> productIds);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update Product p set p.representativeCake = false")
    void updateRepresentativeCakeIsNone();

    @Modifying(clearAutomatically = true)
    @Query(value = "Update Product p set p.representativeCake = true WHERE p.productId in :ids")
    void registerRepresentativeCakeByIds(List<Long> ids);

    List<Product> findAllByRepresentativeCakeIsTrue();

    @Query(value = "SELECT p FROM Product p WHERE p.store.storeId = :storeId")
    Page<Product> findCakesByStoreId(long storeId, Pageable pageable);

    @Query(value = "SELECT p FROM Product p where p.isCake = true and p.popularityRank is not null ORDER BY p.popularityRank")
    List<Product> getPopularCakes();

    List<Product> findTop12ByIsCakeIsTrueOrderByUpdatedAtDesc();
}
