package com.kurly.demo.web;

import com.kurly.demo.service.CartService;
import com.kurly.demo.web.dto.CartRequestDto;
import com.kurly.demo.web.dto.CartListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseBody
    public void save(HttpSession session, CartRequestDto requestDto) {
        cartService.save((Long)session.getAttribute("id"),requestDto);
    }

    @GetMapping
    public String cart(HttpSession session, Model model) {
        List<CartListResponseDto> carts = cartService.findByUserId((Long)session.getAttribute("id"));
        model.addAttribute("totalPrice", carts.stream().mapToInt(s -> s.getCount() * s.getGoods().getPrice()).sum());
        model.addAttribute("carts", carts);


       return "cart/cart";
    }

    @DeleteMapping("/{cartId}")
    @ResponseBody
    public void delete(@PathVariable Long cartId) {
        cartService.delete(cartId);

    }

}
