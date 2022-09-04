package com.project.kcookserver.product.service;

import static com.project.kcookserver.configure.entity.Status.VALID;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.s3.S3Uploader;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.product.dto.CreateProductReq;
import com.project.kcookserver.product.dto.OptionsListRes;
import com.project.kcookserver.product.dto.ProductDetailRes;
import com.project.kcookserver.product.dto.ProductListRes;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.entity.enums.OptionsCategoryType;
import com.project.kcookserver.product.repository.OptionsRepository;
import com.project.kcookserver.product.repository.ProductRepository;
import com.project.kcookserver.product.repository.ProductRepositoryCustom;
import com.project.kcookserver.product.vo.PopularProduct;
import com.project.kcookserver.product.vo.Popularity;
import com.project.kcookserver.store.enums.Area;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
    private final S3Uploader s3Uploader;

    public Page<ProductListRes> getCakeList
            (int page, int size, String sortBy, boolean isAsc, String event, String options, Integer lowPrice, Integer highPrice, Area area) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

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
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> additionalProductList = productRepositoryCustom.findAllAdditionalProduct(pageable, options, lowPrice, highPrice, area);
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
        // Account account = customUserDetails.getAccount();
        Product product = new Product(createProductReq);
        Product save = productRepository.save(product);
        createProductReq.getNewOptionsList().forEach(optionsDto -> {
            if (optionsDto.getCategory().equals(OptionsCategoryType.IMAGE) && optionsDto.getMultipartFile() != null) {
                try {
                    String imageUrl = s3Uploader.upload(optionsDto.getMultipartFile(), "optionsImage");
                    optionsDto.setImageUrl(imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        List<Options> optionsList = createProductReq.getNewOptionsList().stream().map(Options::new).collect(Collectors.toList());
        optionsList.forEach(options -> options.setProduct(save));
        optionsRepository.saveAll(optionsList);
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

    public Page<PopularProduct> getPopularProducts(int page) {
        Sort sort = Sort.by(Direction.ASC, "popularityRank");
        Pageable pageable = PageRequest.of(page, 4, sort);
        return productRepositoryCustom.findAllPopularProducts(pageable);
    }

    public Page<ProductListRes> getProductsByUpdatedAtDesc(int page) {
        Sort sort = Sort.by(Direction.DESC, "updatedAt");
        Pageable pageable = PageRequest.of(page, 4, sort);

        return productRepositoryCustom.findRecentUpdatedProducts(pageable);
    }

    @Transactional
    public void updateRepresentativeCake(List<Long> cakeIds) {
        productRepository.updateRepresentativeCakeIsNone();
        productRepository.registerRepresentativeCakeByIds(cakeIds);
    }

    public List<ProductListRes> getRepresentativeCakes() {
        return productRepository.findAllByRepresentativeCakeIsTrue().stream().map(ProductListRes::new).collect(Collectors.toList());
    }
}
