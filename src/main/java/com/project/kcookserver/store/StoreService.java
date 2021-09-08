package com.project.kcookserver.store;

import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.store.dto.StoreDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.kcookserver.configure.entity.Status.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreDetailRes getStoreInfo(CustomUserDetails customUserDetails) {
        StoreDetailRes storeDetailRes = storeRepository.getStoreBy(customUserDetails.getAccount(), VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
        return storeDetailRes;
    }
}
