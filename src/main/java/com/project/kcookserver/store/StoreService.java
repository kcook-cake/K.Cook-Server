package com.project.kcookserver.store;

import static com.project.kcookserver.configure.entity.Status.VALID;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.store.dto.Coordinate;
import com.project.kcookserver.store.dto.CreateStoreReq;
import com.project.kcookserver.store.dto.StoreDetailRes;
import com.project.kcookserver.store.enums.Area;
import com.project.kcookserver.util.location.NaverGeocode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final NaverGeocode naverGeocode;

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
    public void updateRepresentativeStore(List<Long> storeIds) {
        storeRepository.updateRepresentativeStoreIsNone();
        storeRepository.registerRepresentativeStoreByIds(storeIds);
    }

    public List<StoreDetailRes> getRepresentativeStores() {
        return storeRepository.findAllByRepresentativeStoreIsTrue().stream().map(StoreDetailRes::new).collect(Collectors.toList());
    }

    public Page<StoreDetailRes> getStoresByArea(Area area, int page, int size, boolean isAsc, String sortBy) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return storeRepository.findAllByArea(area, pageable).map(StoreDetailRes::new);
    }
}
