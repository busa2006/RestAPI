package com.kurly.demo.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kurly.demo.web.dto.CartRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@JsonFilter("CartInfo")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int count;

    @Builder
    public Cart(Goods goods, User user,int count) {
        this.goods = goods;
        this.user = user;
        this.count = count;
    }


    public void countChange(CartRequestDto cartRequestDto) {
        this.count = cartRequestDto.getCount();
    }
}
