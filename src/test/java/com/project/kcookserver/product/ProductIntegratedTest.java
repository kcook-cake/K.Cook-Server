// package com.project.kcookserver.product;
//
// import com.project.kcookserver.account.AccountRepository;
// import com.project.kcookserver.account.dto.AccountAuthDto;
// import com.project.kcookserver.account.entity.Account;
// import com.project.kcookserver.product.entity.Product;
// import com.project.kcookserver.product.repository.ProductRepository;
// import com.project.kcookserver.store.Store;
// import com.project.kcookserver.store.StoreRepository;
// import com.project.kcookserver.store.dto.CreateStoreReq;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.time.LocalDate;
//
// import static com.project.kcookserver.configure.entity.Status.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// @Transactional
// @AutoConfigureMockMvc
// @SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
// public class ProductIntegratedTest {
//
//     @Autowired
//     private MockMvc mockMvc;
//
//     @Autowired
//     private ProductRepository productRepository;
//
//     @Autowired
//     private AccountRepository accountRepository;
//
//     @Autowired
//     private StoreRepository storeRepository;
//
//     private Account testAccount;
//     private Store testStore;
//
//     @BeforeEach
//     public void beforeCreateData() {
//         AccountAuthDto accountAuthDto = AccountAuthDto.builder()
//                 .accountId(1L)
//                 .signInId("testUserId")
//                 .email("test@test.com")
//                 .nickname("testUser")
//                 .password("test1234")
//                 .phoneNumber("01012345678")
//                 .address("testAddress")
//                 .dateOfBirth(LocalDate.now()).build();
//         testAccount = accountRepository.save(Account.createAccount(accountAuthDto));
//
//         CreateStoreReq req = CreateStoreReq.builder()
//                 .name("testStore")
//                 .area("testArea")
//                 .address("testAddress")
//                 .contact("testContact")
//                 .build();
//         testStore = storeRepository.save(new Store(req, testAccount));
//     }
//
//     @DisplayName("상품 Detail 조회 테스트")
//     @Test
//     public void signUpTest() throws Exception {
//
//         //given
//         String productName = "testProductName";
//
//         Product testProduct = Product.builder()
//                 .name(productName)
//                 .status(VALID)
//                 .store(testStore)
//                 .reviewCount(0L)
//                 .isCake(true)
//                 .raiting(4.8F)
//                 .price(1000)
//                 .thumbnail("testThumbnail")
//                 .build();
//
//         Product save = productRepository.save(testProduct);
//         Long productId = save.getProductId();
//
//
//         //when
//         ResultActions resultActions = mockMvc.perform(get("/app/products/"+productId)
//                 .accept(MediaType.APPLICATION_JSON));
//
//         //then
//         resultActions.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.isSuccess").value(true))
//                 .andExpect(jsonPath("$.code").value(1000))
//                 .andExpect(jsonPath("$.result").exists())
//                 .andExpect(jsonPath("$.result.productId").value(productId))
//                 .andExpect(jsonPath("$.result.name").value(productName))
//                 .andDo(MockMvcResultHandlers.print());
//
//     }
//
// }
