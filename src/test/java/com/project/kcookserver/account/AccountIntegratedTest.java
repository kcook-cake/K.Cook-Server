package com.project.kcookserver.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.kcookserver.account.dto.AccountAuthDto;
import com.project.kcookserver.account.dto.SignInReq;
import com.project.kcookserver.account.dto.TestAccountAuthDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
public class AccountIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Value("${test.address}")
    private String realAddress;

    AccountAuthDto accountAuthDto;

    @BeforeEach
    private void createBeforeData() {
        accountAuthDto = AccountAuthDto.builder()
                .signInId("testUserId")
                .email("test@test.com")
                .nickname("testUser")
                .password("test1234")
                .phoneNumber("01012345678")
                .address(realAddress)
                .dateOfBirth(LocalDate.now()).build();
    }

    @DisplayName("회원가입 테스트")
    @Test
    public void signUpTest() throws Exception {

        //given
        TestAccountAuthDto testAccountAuthDto = new TestAccountAuthDto(accountAuthDto);
        testAccountAuthDto.setPassword(accountAuthDto.getPassword());
        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(testAccountAuthDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/app/sign-up")
                .contentType(MediaType.APPLICATION_JSON).content(content).accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.result").exists())
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("로그인 테스트")
    @Test
    public void signInTest() throws Exception {

        //given
        accountService.signUp(accountAuthDto);
        SignInReq signInReq = SignInReq.builder()
                .signInId("testUserId")
                .password("test1234").build();
        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(signInReq);

        //when
        ResultActions resultActions = mockMvc.perform(post("/app/sign-in")
                .contentType(MediaType.APPLICATION_JSON).content(content).accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.result.jwt").exists())
                .andDo(MockMvcResultHandlers.print());

    }

}
