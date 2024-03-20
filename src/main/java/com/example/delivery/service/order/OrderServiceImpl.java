package com.example.delivery.service.order;

import com.example.delivery.domain.Order;
import com.example.delivery.domain.OrderMenu;
import com.example.delivery.domain.Store;
import com.example.delivery.domain.User;
import com.example.delivery.dto.OrderDto;
import com.example.delivery.dto.OrderMenuDto;
import com.example.delivery.repository.OrderRepository;
import com.example.delivery.repository.StoreRepository;
import com.example.delivery.service.ordermenu.OrderMenuService;
import com.example.delivery.service.store.StoreService;
import com.example.delivery.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final StoreService storeService;
    private final OrderMenuService orderMenuService;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

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
    public List<OrderDto.DetailResponse> getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다"));

        List<OrderDto.DetailResponse> menuDetails = new ArrayList<>();
        List<OrderMenu> orderMenus = order.getOrderMenus();
        for (OrderMenu orderMenu : orderMenus) {
            menuDetails.add(
                    OrderDto.DetailResponse.builder()
                            .name(orderMenu.getMenu().getName())
                            .quantity(orderMenu.getQuantity())
                            .totalPrice(orderMenu.getTotalPrice())
                            .build()
            );
        }

        return menuDetails;
    }

    @Override
    public List<OrderDto.StatusResponse> getOrdersByUserId(User user) {
        Store store = storeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("가게가 존재하지 않습니다."));

        return orderRepository.findAllByStoreId(store.getId())
                .stream()
                .map(OrderDto.StatusResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public void updateArrived(Long orderId) {
        Order findOrder = findById(orderId);
        findOrder.updateIsArrived(true); // 배달 완료 처리

        // 총 주문 금액 음식점 매출에 더해줌
        List<OrderMenu> orderMenus = findOrder.getOrderMenus();
        double orderTotalPrice = 0.0;
        for (OrderMenu orderMenu : orderMenus) {
            orderTotalPrice += orderMenu.getTotalPrice();
        }

        Store store = findOrder.getStore();
        store.plusSales(orderTotalPrice);
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("주문이 존재하지 않습니다."));
    }

}
