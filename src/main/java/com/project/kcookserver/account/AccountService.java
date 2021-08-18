package com.project.kcookserver.account;

import com.project.kcookserver.account.dto.AccountAuthDto;
import com.project.kcookserver.account.dto.SignInReq;
import com.project.kcookserver.account.dto.SignInRes;
import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.configure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.kcookserver.configure.entity.Status.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AccountAuthDto signUp(AccountAuthDto dto) {
        if (accountRepository.findByEmailAndStatus(dto.getEmail(), VALID).isPresent()) throw new CustomException(CustomExceptionStatus.DUPLICATED_EMAIL);
        if (accountRepository.findByNicknameAndStatus(dto.getNickname(), VALID).isPresent()) throw new CustomException(CustomExceptionStatus.DUPLICATED_NICKNAME);
        if (accountRepository.findBySignInIdAndStatus(dto.getSignInId(), VALID).isPresent()) throw new CustomException(CustomExceptionStatus.DUPLICATED_ID);

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Account account = Account.createAccount(dto);
        Account save = accountRepository.save(account);
        dto.setAccountId(save.getAccountId());
        dto.setJwt(jwtTokenProvider.createToken(account.getSignInId(),account.getRole()));
        return dto;
    }

    @Transactional
    public SignInRes signIn(SignInReq req) {
        Account account = accountRepository.findBySignInIdAndStatus(req.getSignInId(), VALID)
                .orElseThrow(()-> new CustomException(CustomExceptionStatus.FAILED_TO_LOGIN));
        if(!passwordEncoder.matches(req.getPassword(),account.getPassword())){
            throw new CustomException(CustomExceptionStatus.FAILED_TO_LOGIN);
        }

        SignInRes res = SignInRes.builder()
                .accountId(account.getAccountId())
                .jwt(jwtTokenProvider.createToken(account.getSignInId(), account.getRole()))
                .build();

        return res;
    }

    public AccountAuthDto getAuthAccount(CustomUserDetails customUserDetails) {
        Account account = customUserDetails.getAccount();
        return new AccountAuthDto(account);
    }

}

