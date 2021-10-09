package com.kurly.demo.web.dto;

import com.kurly.demo.domain.Cart;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListResponseDto {
    private Long id;
    private Goods goods;
    private User user;
    private int count;
    private int totalPrice;

    public CartListResponseDto(Cart cart) {
        this.id = cart.getId();
        this.goods = cart.getGoods();
        this.user = cart.getUser();
        this.count = cart.getCount();
        this.totalPrice = cart.getCount() * cart.getGoods().getPrice();
    }
}
