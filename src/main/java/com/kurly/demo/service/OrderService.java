package com.kurly.demo.service;

import com.kurly.demo.domain.*;
import com.kurly.demo.repository.CartRepository;
import com.kurly.demo.repository.GoodsRepository;
import com.kurly.demo.repository.OrderRepository;
import com.kurly.demo.repository.UserRepository;
import com.kurly.demo.web.api.CustomNotFoundException;
import com.kurly.demo.web.dto.CartRequestDto;
import com.kurly.demo.web.dto.OrderRequestDto;
import com.kurly.demo.web.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final CartRepository cartRepository;

    @Transactional
    public Order save(Long userId, OrderRequestDto orderRequestDto) {

        Optional<User> user = userRepository.findById(userId);
        List<Cart> carts = cartRepository.findByUserId(userId);

        Delivery delivery = new Delivery();
        delivery.setAddress(orderRequestDto.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        List<OrderGoods> orderGoods = OrderGoods.createOrderGoods(carts);
        Order order = Order.createOrder(orderGoods,user,delivery);
        orderRepository.save(order);
        cartRepository.deleteByUserId(userId);

        return order;
    }

    public List<OrderResponseDto> findByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return OrderResponseDto.createOrderResponseDtos(orders);
    }

    public List<OrderGoods> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.get().getOrderGoods();
    }

    public List<Order> findOrderByUserId(Long userId) {
         return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Optional<Order> order = orderRepository.findById(orderId);
        //주문 취소
        if(order.isPresent())
            order.get().cancel();
        else{
            throw new CustomNotFoundException(
                    String.format("Order ID [%s] could not be found.", orderId)
            );
        }

        return order.get();
    }
}
