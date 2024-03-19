package com.example.delivery.service.order;

import com.example.delivery.domain.Order;
import com.example.delivery.dto.OrderDto;
import com.example.delivery.dto.OrderMenuDto;
import com.example.delivery.repository.OrderRepository;
import com.example.delivery.service.ordermenu.OrderMenuService;
import com.example.delivery.service.store.StoreService;
import com.example.delivery.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final StoreService storeService;
    private final OrderMenuService orderMenuService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void createOrder(List<OrderMenuDto.MenuRequest> requestDto, String email, Integer storeId) {

        Order order = orderRepository.save(
                Order.builder()
                .user(userService.findUserByEmail(email))
                .store(storeService.findStoreId(storeId))
                .isArrived(false)
                .build());

        for(OrderMenuDto.MenuRequest menuRequest : requestDto){
            orderMenuService.createOrderMenu(menuRequest, order);
        }
    }

    @Override
    public OrderDto.Get getOrder(Long orderId) {
        return OrderDto.Get.builder().order(findById(orderId)).build();
    }

    @Override
    public List<OrderDto.Get> getOrdersByUserId(String email) {
        return orderRepository.findAllByUserId(userService.findUserByEmail(email).getId()).stream().map(OrderDto.Get::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateArrived(Long orderId) {
        findById(orderId).updateIsArrived(true);
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("주문이 존재하지 않습니다."));
    }

}
