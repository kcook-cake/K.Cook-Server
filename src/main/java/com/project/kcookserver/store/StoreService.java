package com.project.kcookserver.store;

import static com.project.kcookserver.configure.entity.Status.VALID;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.s3.S3Uploader;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.store.dto.Coordinate;
import com.project.kcookserver.store.dto.CreateStoreReq;
import com.project.kcookserver.store.dto.DefaultPageStore;
import com.project.kcookserver.store.dto.StoreDetailRes;
import com.project.kcookserver.store.enums.Area;
import com.project.kcookserver.util.location.NaverGeocode;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final NaverGeocode naverGeocode;

    private final S3Uploader s3Uploader;

    public StoreDetailRes getStoreInfo(CustomUserDetails customUserDetails) {
        StoreDetailRes storeDetailRes = storeRepository.getStoreByAccountAndStatus(customUserDetails.getAccount(), VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.STORE_NOT_FOUND));
        return storeDetailRes;
    }

    @Transactional
    public Long createStoreByAccount(CustomUserDetails customUserDetails, CreateStoreReq dto) {
        Account account = customUserDetails.getAccount();
        Optional<StoreDetailRes> optional = storeRepository.getStoreByAccountAndStatus(account, VALID);
        if (optional.isPresent()) throw new CustomException(CustomExceptionStatus.ALREADY_CREATED_STORE);
        Coordinate coordinate = naverGeocode.getCoordinate(dto.getAddress());
        Store store = new Store(dto, account, coordinate);
        Store save = storeRepository.save(store);
        return save.getStoreId();
    }

    @Transactional
    public void updateStoreByAccount(CustomUserDetails customUserDetails, CreateStoreReq dto) {
        Account account = customUserDetails.getAccount();
        Store store = storeRepository.findByAccountAndStatus(account, VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.STORE_NOT_FOUND));
        naverGeocode.getCoordinate(dto.getAddress());
        store.updateStore(dto);
    }

    @Transactional
    public void updateRepresentativeStore(List<DefaultPageStore> defaultPageStores) {
        storeRepository.updateDefaultPageStoreIsNone();

        List<Long> cakeIds = defaultPageStores.stream().map(DefaultPageStore::getStoreId).collect(Collectors.toList());
        Map<Long, Store> products = storeRepository.findByStoreIdIn(cakeIds).stream().collect(Collectors.toMap(
                Store::getStoreId,
                Function.identity()
        ));

        for (DefaultPageStore defaultPageStore : defaultPageStores) {
            products.get(defaultPageStore.getStoreId())
                    .changeDefaultPageSequence(defaultPageStore.getSequence());
        }
    }

    public List<StoreDetailRes> getDefaultPageStores() {
        return storeRepository.findStoreByDefaultPageStoreSequenceIsNotNull().stream().map(StoreDetailRes::new)
                .collect(Collectors.toList());
    }

    public Page<StoreDetailRes> getStoresByArea(Area area, int page, int size, boolean isAsc, String sortBy) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return storeRepository.findAllByArea(area, pageable).map(StoreDetailRes::new);
    }

    @Transactional
    public void updateStoreImage (
            long storeId,
            MultipartFile storeImage1,
            MultipartFile storeImage2,
            MultipartFile storeImage3,
            MultipartFile storeImage4,
            MultipartFile storeImage5){
        List<String> storeImages = Stream.of(storeImage1, storeImage2, storeImage3, storeImage4, storeImage5)
                .map(
                        multipartFile -> {
                            if (multipartFile == null) {
                                return "";
                            }

                            try {
                                return s3Uploader.upload(multipartFile, "storeImage");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).collect(Collectors.toList());

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토어입니다"));
        store.setStoreImages(storeImages);
    }
}
