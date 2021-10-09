package com.kurly.demo.service;

import com.kurly.demo.domain.Cart;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.domain.User;
import com.kurly.demo.repository.CartRepository;
import com.kurly.demo.repository.GoodsRepository;
import com.kurly.demo.repository.UserRepository;
import com.kurly.demo.web.api.CustomNotFoundException;
import com.kurly.demo.web.dto.CartRequestDto;
import com.kurly.demo.web.dto.CartListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(Long userId, CartRequestDto requestDto) {
        Optional<User> user = userRepository.findById(userId);
        Goods goods = goodsRepository.findOne(requestDto.getGoodsId());
        cartRepository.save(Cart.builder().user(user.get()).goods(goods).count(requestDto.getCount()).build());
    }

    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public List<Cart> findCartsByUserId(Long UserId) {
        return cartRepository.findAllByUserId(UserId);
    }

    public List<CartListResponseDto> findByUserId(Long userId){
        return cartRepository.findByUserId(userId).stream()
                .map(CartListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(Long cartId){

        cartRepository.deleteById(cartId);
    }

    @Transactional
    public void updateCart(Long cartId, CartRequestDto cartRequestDto) {

        Optional<Cart> cart = cartRepository.findById(cartId);

        if(cart.isPresent())
            cart.get().countChange(cartRequestDto);
        else{
            throw new CustomNotFoundException(
                    String.format("Cart ID [%s] could not be found.", cartId)
            );
        }

    }
}
