package com.project.kcookserver.configure.security.authentication;

import com.project.kcookserver.account.AccountRepository;
import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.kcookserver.configure.entity.Status.*;


@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmailAndStatus(email, VALID);
        if (!optionalAccount.isPresent()) throw new CustomException(CustomExceptionStatus.ACCOUNT_NOT_FOUND);
        return new CustomUserDetails(optionalAccount.get());
    }

}
