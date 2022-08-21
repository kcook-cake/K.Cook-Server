package com.project.kcookserver.product.repository;

import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.dto.QProductListRes;
import com.project.kcookserver.product.entity.*;
import com.project.kcookserver.product.vo.PopularProduct;
import com.project.kcookserver.product.vo.QPopularProduct;
import com.project.kcookserver.store.QStore;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import static com.project.kcookserver.configure.entity.Status.*;
import static com.project.kcookserver.product.entity.QProduct.product;
import static com.project.kcookserver.store.QStore.store;


@RequiredArgsConstructor
@Repository
public class ProductQueryRepository implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private OrderSpecifier<?>[] getSortedColumn(Sort sorts){
        return sorts.toList().stream().map(x ->{
            Order order = x.getDirection().name() == "ASC"? Order.ASC : Order.DESC;
            SimplePath<Object> filedPath = Expressions.path(Object.class, QProduct.product, x.getProperty());
            return new OrderSpecifier(order, filedPath);
        }).toArray(OrderSpecifier[]::new);
    }



    @Override
    public Page<Product> findAllCakeProduct(Pageable pageable, String event, String options, Integer lowPrice, Integer highPrice, String area) {
        QProduct product = QProduct.product;
        QStore store = QStore.store;
        QProductEventRelation productEventRelation = QProductEventRelation.productEventRelation;
        QEvent qEvent = QEvent.event;
        QOptions qOptions = QOptions.options;

        QueryResults<Product> result = queryFactory
                .select(product)
                .from(product)
                .leftJoin(product.store, store).fetchJoin()
                .leftJoin(productEventRelation).on(productEventRelation.product.eq(product).and(productEventRelation.status.eq(VALID)))
                .leftJoin(qEvent).on(qEvent.status.eq(VALID).and(productEventRelation.event.eq(qEvent)))
                .leftJoin(qOptions).on(qOptions.status.eq(VALID))
                .where(product.status.eq(VALID), product.isCake.eq(true), eventEq(qEvent, event), optionsEq(qOptions, options), storeEq(store, area), lowPriceGoe(product,lowPrice),highPriceLoe(product,highPrice))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getSortedColumn(pageable.getSort()))
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<Product> findAllAdditionalProduct(Pageable pageable, String options, Integer lowPrice, Integer highPrice, String area) {
        QProduct product = QProduct.product;
        QStore store = QStore.store;
        QProductEventRelation productEventRelation = QProductEventRelation.productEventRelation;
        QOptions qOptions = QOptions.options;

        QueryResults<Product> result = queryFactory
                .select(product)
                .from(product)
                .leftJoin(product.store, store).fetchJoin()
                .leftJoin(productEventRelation).on(productEventRelation.product.eq(product).and(productEventRelation.status.eq(VALID)))
                .leftJoin(qOptions).on(qOptions.status.eq(VALID))
                .where(product.status.eq(VALID), product.isCake.eq(false), optionsEq(qOptions, options), storeEq(store, area), lowPriceGoe(product,lowPrice),highPriceLoe(product,highPrice))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getSortedColumn(pageable.getSort()))
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<PopularProduct> findAllPopularProducts(Pageable pageable) {
        QueryResults<PopularProduct> result = queryFactory
            .select(new QPopularProduct(product.popularityRank, product.name, product.price, product.image, store.name))
            .from(product)
            .where(product.popularityRank.isNotNull())
            .leftJoin(store)
            .on(product.store.storeId.eq(store.storeId))
            .orderBy(getSortedColumn(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<ProductListRes> findRecentUpdatedProducts(Pageable pageable) {
        QueryResults<ProductListRes> result = queryFactory
            .select(new QProductListRes(product.name, product.price, product.store.name, product.image))
            .from(product)
            .where(product.isCake.eq(true))
            .leftJoin(store)
            .on(product.store.storeId.eq(store.storeId))
            .orderBy(getSortedColumn(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression eventEq(QEvent qEvent, String event) {
        if (event == null) return null;
        return qEvent.name.eq(event);
    }

    private BooleanExpression optionsEq(QOptions qOptions, String options) {
        if (options == null) return null;
        return qOptions.contents.eq(options);
    }

    private BooleanExpression storeEq(QStore qStore, String area) {
        if (area == null) return null;
        return qStore.area.eq(area);
    }

    private BooleanExpression lowPriceGoe(QProduct qProduct, Integer lowPrice) {
        if (lowPrice == null) return null;
        return qProduct.price.goe(lowPrice);
    }

    private BooleanExpression highPriceLoe(QProduct qProduct, Integer highPrice) {
        if (highPrice == null) return null;
        return qProduct.price.loe(highPrice);
    }
}
