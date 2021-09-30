package com.project.kcookserver.coupon;

import com.project.kcookserver.account.AccountRepository;
import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.coupon.entity.GetCouponReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.kcookserver.configure.entity.Status.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CouponService {

    private final AccountRepository accountRepository;

    public List<GetCouponReq> getCouponsByAccountId(Long accountId) {
        Account account = accountRepository.findByAccountIdAndStatus(accountId, VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND));
        return account.getCoupons().stream().map(GetCouponReq::new).collect(Collectors.toList());
    }
}
