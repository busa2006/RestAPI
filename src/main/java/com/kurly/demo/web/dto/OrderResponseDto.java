package com.kurly.demo.web.dto;

import com.kurly.demo.domain.Order;
import com.kurly.demo.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class OrderResponseDto {

    private String orderDate;
    private String firstItem;
    private Long id;
    private int price;
    private String status;

    public static List<OrderResponseDto> createOrderResponseDtos(List<Order> orders) {
        List<OrderResponseDto> orderResponseDtos = new ArrayList<OrderResponseDto>();

        for(Order order : orders){
            orderResponseDtos.add(OrderResponseDto.builder()
                    .orderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .firstItem(order.getOrderGoods().get(0).getGoods().getName() +
                            (order.getOrderGoods().size()>1 ? " 외 " + (order.getOrderGoods().size()-1) + "건" : "" )  )
                    .id(order.getId())
                    .price(order.getOrderGoods().stream()
                            .mapToInt(s -> s.getGoods().getPrice()).sum())
                    .status( statusToString(order.getStatus()) )
                    .build());

        }

        return orderResponseDtos.stream().sorted(Comparator.comparing(OrderResponseDto::getOrderDate).reversed()).collect(Collectors.toList());
    }

    private static String statusToString(OrderStatus status) {
        String msg = "";
        switch (status){
            case ORDER :
                msg = "주문";
                break;
            case CANCEL:
                msg = "취소";
                break;
        }
        return msg;
    }
}
