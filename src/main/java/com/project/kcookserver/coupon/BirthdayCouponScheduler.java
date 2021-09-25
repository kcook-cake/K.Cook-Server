package com.project.kcookserver.coupon;

import com.project.kcookserver.account.AccountRepository;
import com.project.kcookserver.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
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
                log.info(account.getNickname() +" : "+ birthdayCoupon.getContents() + " Created");
            }
        }
    }

}
