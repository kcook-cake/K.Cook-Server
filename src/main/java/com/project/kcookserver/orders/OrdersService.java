package com.project.kcookserver.orders;

import com.project.kcookserver.account.entity.Account;
import com.project.kcookserver.configure.response.exception.CustomException;
import com.project.kcookserver.configure.response.exception.CustomExceptionStatus;
import com.project.kcookserver.configure.security.authentication.CustomUserDetails;
import com.project.kcookserver.orders.dto.CreateOrdersReq;
import com.project.kcookserver.orders.dto.OrdersListRes;
import com.project.kcookserver.orders.entity.Orders;
import com.project.kcookserver.product.entity.Options;
import com.project.kcookserver.product.entity.Product;
import com.project.kcookserver.product.repository.OptionsRepository;
import com.project.kcookserver.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrdersService {

    private final OptionsRepository optionsRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long createOrders(CustomUserDetails customUserDetails, CreateOrdersReq createOrdersReq) {
        Account account = customUserDetails.getAccount();
        Product product = productRepository.findById(createOrdersReq.getProductId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.PRODUCT_NOT_FOUND));
        List<Options> optionsList = new ArrayList<>();
        for (Long optionsId : createOrdersReq.getOrdersList()) {
            optionsList.add(optionsRepository.findById(optionsId)
                    .orElseThrow(() -> new CustomException(CustomExceptionStatus.OPTIONS_NOT_FOUND)));
        }

        Orders orders = new Orders(account, product, createOrdersReq.getPaymentType(), createOrdersReq.getPickUpAt(), optionsList);
        Orders save = ordersRepository.save(orders);

        return save.getOrdersId();
    }

    public List<OrdersListRes> getOrdersListByAccount(CustomUserDetails customUserDetails) {
        Account account = customUserDetails.getAccount();
        return ordersRepository.findAllByAccount(account);
    }
}
