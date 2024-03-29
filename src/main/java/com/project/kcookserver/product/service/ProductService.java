package com.project.kcookserver.product.service;

import static com.project.kcookserver.configure.entity.Status.VALID;
import static org.springframework.data.domain.Sort.by;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.s3.S3Uploader;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.product.dto.*;
import com.project.kcookserver.product.dto.ChildOptionsListRes;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.product.dto.OptionsListRes;
import com.project.kcookserver.product.dto.ProductDetailRes;
import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.entity.ChildOptions;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.repository.ChildOptionsRepository;
import com.project.kcookserver.product.repository.OptionsRepository;
import com.project.kcookserver.product.repository.ProductRepository;
import com.project.kcookserver.product.repository.ProductRepositoryCustom;
import com.project.kcookserver.product.vo.Popularity;
import com.project.kcookserver.store.enums.Area;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OptionsRepository optionsRepository;
    private final ProductRepositoryCustom productRepositoryCustom;

    private final ChildOptionsRepository childOptionsRepository;

    private final S3Uploader s3Uploader;

    public Page<ProductListRes> getCakeList
            (int page, int size, String sortBy, boolean isAsc, String event, String options, Integer lowPrice, Integer highPrice, Area area) {
        Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> cakeList;
        try {
            cakeList = productRepositoryCustom.findAllCakeProduct(pageable, event, options, lowPrice, highPrice, area);
        } catch (Exception exception) {
            throw new CustomException(CustomExceptionStatus.REQUEST_ERROR);
        }
        return cakeList.map(ProductListRes::new);
    }

    public Page<ProductListRes> getAdditionalProductsList
            (Integer page, Integer size, String sortBy, Boolean isAsc, String options, Integer lowPrice, Integer highPrice, Area area) {
        Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> additionalProductList = productRepositoryCustom.findAllAdditionalProduct(pageable, options, lowPrice, highPrice, area);
        return additionalProductList.map(ProductListRes::new);
    }

    public ProductDetailRes getDetailProduct(Long productId) {
        Product product = productRepository.findByProductIdAndStatus(productId, VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
        List<OptionsListRes> optionsList = new ArrayList<>();
        optionsRepository.findAllByProductAndStatus(product, VALID)
            .forEach(
                options -> {
                    OptionsListRes optionsListRes = new OptionsListRes(options);
                    ArrayList<ChildOptionsListRes> childOptionsListResList = new ArrayList<>();
                    childOptionsRepository.findAllByOptionsAndStatus(options, VALID).forEach(
                        childOptions -> childOptionsListResList.add(new ChildOptionsListRes(childOptions)));
                    optionsListRes.setChildOptionsList(childOptionsListResList);
                    optionsList.add(optionsListRes);
                }
            );
        ProductDetailRes productDetailRes = new ProductDetailRes(product, optionsList);
        return productDetailRes;
    }

    @Transactional
    public Long createProduct(CustomUserDetails customUserDetails, CreateProductReq createProductReq) {
        Account account = customUserDetails.getAccount();
        if (account.getStore() == null) throw new CustomException(CustomExceptionStatus.STORE_NOT_FOUND);
        Product product = new Product(createProductReq, account);
        Product save = productRepository.save(product);
        if (createProductReq.getNewOptionsList() != null) {
            createProductReq.getNewOptionsList().forEach(
                options->{
                    Options optionsEntity = new Options(options);
                    optionsEntity.setProduct(save);
                    Options savedOptions = optionsRepository.save(optionsEntity);
                    if (options.getChild()!= null){
                        options.getChild()
                            .forEach(child -> {
                                    ChildOptions childOptions = new ChildOptions(child);
                                    childOptions.setOptions(savedOptions);
                                    childOptionsRepository.save(childOptions);
                            }
                        );
                    }
                }
            );
        }
        return save.getProductId();
    }
    @Transactional
    public void uploadProductImage(MultipartFile multipartFile, Long productId) throws IOException {
        String productImageUrl = s3Uploader.upload(multipartFile, "productImage");
        Product product = productRepository.findByProductIdAndStatus(productId, VALID)
            .orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
        product.setImage(productImageUrl);
    }

    @Transactional
    public void updatePopularity(List<Popularity> popularities) {
        List<Product> popularProducts = productRepository.findByPopularityRankIsNotNull();
        popularProducts.stream().forEach(Product::deletePopularityRank);

        List<Long> cakeIds = popularities.stream().map(Popularity::getCakeId).collect(Collectors.toList());
        Map<Long, Product> newPopularProducts = productRepository.findByProductIdIn(cakeIds).stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
        for (Popularity popularity : popularities) {
            newPopularProducts.get(popularity.getCakeId()).changePopularityRank(popularity.getPopularityRank());
        }
    }

    public List<ProductListRes> getPopularProducts() {
        List<Product> popularCakes = productRepository.getPopularCakes();
        return popularCakes.stream().map(ProductListRes::new).collect(Collectors.toList());
    }

    public List<ProductListRes> getProductsByUpdatedAtDesc() {
        List<Product> products = productRepository.findTop12ByIsCakeIsTrueOrderByUpdatedAtDesc();
        return products.stream().map(ProductListRes::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateDefaultPageCake(List<DefaultPageCake> defaultPageCakes) {
        productRepository.updateDefaultPageCakeIsNone();

        List<Long> cakeIds = defaultPageCakes.stream().map(DefaultPageCake::getProductId).collect(Collectors.toList());
        Map<Long, Product> products = productRepository.findByProductIdIn(cakeIds).stream().collect(Collectors.toMap(
                Product::getProductId,
                Function.identity()
        ));

        for (DefaultPageCake defaultPageCake : defaultPageCakes) {
            products.get(defaultPageCake.getProductId())
                    .changeDefaultPageSequence(defaultPageCake.getSequence());
        }
    }

    public List<ProductListRes> getDefaultPageCakes() {
        return productRepository.getDefaultPageCakes().stream().map(ProductListRes::new)
                .collect(Collectors.toList());
    }

    public Page<ProductListRes> getCakesByStoreId(long storeId, int page, int size, boolean isAsc, String sortBy) {
        Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findCakesByStoreId(storeId, pageable).map(ProductListRes::new);
    }

    @Transactional
    public void addProductImages(Long productId, MultipartFile productImage1, MultipartFile productImage2,
        MultipartFile productImage3, MultipartFile productImage4, MultipartFile productImage5,
        MultipartFile optionImage1, MultipartFile optionImage2, MultipartFile optionImage3) {
        List<String> productImage = Stream.of(productImage1, productImage2, productImage3, productImage4, productImage5,
            optionImage1, optionImage2, optionImage3).map(
            multipartFile -> {
                if (multipartFile == null) {
                    return "";
                }
                else {
                    try {
                        return s3Uploader.upload(multipartFile, "productImage");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        ).collect(Collectors.toList());
        Product product = productRepository.findByProductIdAndStatus(productId, VALID)
            .orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
        product.setProductImages(productImage);
    }
}
