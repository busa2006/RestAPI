package com.kurly.demo.web.api;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kurly.demo.domain.Cart;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.service.CartService;
import com.kurly.demo.web.dto.CartRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartApiController {

    private final CartService cartService;

    @GetMapping("/v1/carts/{userId}")
    public MappingJacksonValue retrieveAllCartByUserId(@PathVariable Long userId) {

        List<Cart> carts = cartService.findCartsByUserId(userId);
        if(carts == null){
            throw new CustomNotFoundException(
                    String.format("The Cart for the user with ID [%s] could not be found.", userId)
            );
        }

        SimpleBeanPropertyFilter cartFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","goods","count");

        SimpleBeanPropertyFilter GoodsFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","img","price","name");

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("CartInfo",cartFilter)
                .addFilter("GoodsInfo",GoodsFilter);

        MappingJacksonValue mapping = new MappingJacksonValue(carts);
        mapping.setFilters(filters);
        return mapping;
    }

    @PutMapping("/v1/carts/{cartId}")
    public void changeCartById(@PathVariable Long cartId,@Valid @RequestBody CartRequestDto cartRequestDto) {
        cartService.updateCart(cartId,cartRequestDto);
    }




}
