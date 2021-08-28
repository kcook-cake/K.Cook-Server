package com.project.kcookserver.account.coupon;

import com.project.kcookserver.account.AccountRepository;
import com.project.kcookserver.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class BirthdayCouponScheduler {

    private final AccountRepository accountRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void giveBirthdayCouponToAccount() {
        List<Account> allAccount = accountRepository.findAll();
        for (Account account : allAccount) {
            if (account.getDateOfBirth().equals(LocalDate.now())) {
                Coupon birthdayCoupon = new Coupon(account, 1000, "생일 축하 쿠폰", 30);
                account.getCoupons().add(birthdayCoupon);
            }
        }
    }

}
