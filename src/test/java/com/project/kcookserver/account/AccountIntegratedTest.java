package com.project.kcookserver.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.kcookserver.account.dto.AccountAuthDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 테스트")
    @Test
    public void signUpTest() throws Exception {
        //given
        AccountAuthDto accountAuthDto = AccountAuthDto.builder()
                .signInId("testUserId")
                .email("test@test.com")
                .nickname("testUser")
                .password("test1234")
                .phoneNumber("01012345678")
                .address("서울시 동작구 사당로 20나길 19")
                .dateOfBirth(LocalDate.now()).build();
        accountAuthDto.setPassword(passwordEncoder.encode(accountAuthDto.getPassword()));
        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(accountAuthDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/app/sign-up")
                .contentType(MediaType.APPLICATION_JSON).content(content).accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(MockMvcResultHandlers.print());

    }

}
