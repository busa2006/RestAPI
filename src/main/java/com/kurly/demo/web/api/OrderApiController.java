package com.kurly.demo.web.api;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kurly.demo.domain.Cart;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.domain.Order;
import com.kurly.demo.domain.OrderGoods;
import com.kurly.demo.service.CartService;
import com.kurly.demo.service.OrderService;
import com.kurly.demo.web.dto.Message;
import com.kurly.demo.web.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping(value = "/v1/orders")
    public ResponseEntity<Order> save(OrderRequestDto orderRequestDto) {

        Order orders = orderService.save(orderRequestDto.getUserId(), orderRequestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orders.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/v1/orders/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderService.cancelOrder(orderId);
        Message message = Message.builder().msg("주문번호 " + order.getId() +" 취소되었습니다.").build();
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    @GetMapping("/v1/users/{userId}/orders")
    public MappingJacksonValue retrieveAllOrderByUserId(@PathVariable Long userId) {
      List<Order> orders = orderService.findOrderByUserId(userId);

      SimpleBeanPropertyFilter orderFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","orderGoods","delivery","orderDate","status");

      SimpleBeanPropertyFilter deliveryFilter = SimpleBeanPropertyFilter
            .filterOutAllExcept("id","address","status");

      SimpleBeanPropertyFilter orderGoodsFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","goods","count");

      SimpleBeanPropertyFilter goodsFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","img","price","name");

      FilterProvider filters = new SimpleFilterProvider()
                .addFilter("OrderInfo",orderFilter)
                .addFilter("DeliveryInfo",deliveryFilter)
                .addFilter("OrderGoodsInfo",orderGoodsFilter)
                .addFilter("GoodsInfo",goodsFilter);
      MappingJacksonValue mapping = new MappingJacksonValue(orders);
      mapping.setFilters(filters);


      return mapping;

    }


}
