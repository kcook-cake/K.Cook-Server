package com.project.kcookserver.account;

import com.project.kcookserver.account.dto.AccountAuthDto;
import com.project.kcookserver.account.dto.PasswordDto;
import com.project.kcookserver.account.dto.SignInReq;
import com.project.kcookserver.account.dto.SignInRes;
import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.account.entity.enumtypes.RoleType;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.configure.security.jwt.JwtTokenProvider;
import com.project.kcookserver.util.location.NaverGeocode;
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
    private final NaverGeocode naverGeocode;

    @Transactional
    public AccountAuthDto signUp(AccountAuthDto dto) {
        if (accountRepository.findByEmailAndStatus(dto.getEmail(), VALID).isPresent()) throw new CustomException(CustomExceptionStatus.DUPLICATED_EMAIL);
        if (accountRepository.findByNicknameAndStatus(dto.getNickname(), VALID).isPresent()) throw new CustomException(CustomExceptionStatus.DUPLICATED_NICKNAME);
        if (dto.getAddress() != null) naverGeocode.getCoordinate(dto.getAddress());

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Account account = Account.createAccount(dto);
        Account save = accountRepository.save(account);
        dto.setAccountId(save.getAccountId());
        dto.setJwt(jwtTokenProvider.createToken(account.getEmail(),account.getRole()));
        return dto;
    }

    @Transactional
    public SignInRes signIn(SignInReq req) {
        Account account = accountRepository.findByEmailAndStatus(req.getEmail(), VALID)
                .orElseThrow(()-> new CustomException(CustomExceptionStatus.FAILED_TO_LOGIN));
        if(!passwordEncoder.matches(req.getPassword(),account.getPassword())){
            throw new CustomException(CustomExceptionStatus.FAILED_TO_LOGIN);
        }

        SignInRes res = SignInRes.builder()
                .accountId(account.getAccountId())
                .jwt(jwtTokenProvider.createToken(account.getEmail(), account.getRole()))
                .build();

        return res;
    }

    public AccountAuthDto getAuthAccount(CustomUserDetails customUserDetails) {
        Account account = customUserDetails.getAccount();
        return new AccountAuthDto(account);
    }

    @Transactional
    public void updateAccountRoleByAccountsEmail(String email, RoleType roleType) {
        Account account = accountRepository.findByEmailAndStatus(email, VALID)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_VALID));
        account.changeRole(roleType);
    }

    @Transactional
    public void updateAccountPasswordByEmail(PasswordDto dto) {
        Account account = accountRepository.findByEmailAndStatus(dto.getEmail(), VALID)
            .orElseThrow(() -> new CustomException(CustomExceptionStatus.ACCOUNT_NOT_VALID));
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    @Transactional
	public void deleteAccountByAccountPassword(CustomUserDetails customUserDetails, PasswordDto.OnlyPasswordDto passwordDto) {
        Account account = accountRepository.findByEmailAndStatus(customUserDetails.getAccount().getEmail(), VALID)
            .orElseThrow(()-> new CustomException(CustomExceptionStatus.FAILED_TO_LOGIN));
        if(!passwordEncoder.matches(passwordDto.getPassword(),account.getPassword())){
            throw new CustomException(CustomExceptionStatus.FAILED_TO_LOGIN);
        }
        account.setDELETED();
	}
}

