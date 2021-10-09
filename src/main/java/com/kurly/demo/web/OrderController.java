package com.kurly.demo.web;

import com.kurly.demo.domain.Cart;
import com.kurly.demo.service.CartService;
import com.kurly.demo.service.OrderService;
import com.kurly.demo.web.dto.CartRequestDto;
import com.kurly.demo.web.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping
    @ResponseBody
    public void save(HttpSession session, OrderRequestDto orderRequestDto) {
        orderService.save((Long)session.getAttribute("id"), orderRequestDto);
    }

    @GetMapping("/{address}")
    public String order(HttpSession session, Model model, @PathVariable String address) {
        model.addAttribute("carts", cartService.findByUserId((Long)session.getAttribute("id")));
        model.addAttribute("address",address);
        return "order/order";
    }

    @GetMapping("/list")
    public String orderList(HttpSession session, Model model) {
        model.addAttribute("orders", orderService.findByUserId((Long)session.getAttribute("id")));
        return "order/list";
    }

    @GetMapping("/list/{id}")
    public String orderDetail(HttpSession session, Model model,@PathVariable Long id) {
        model.addAttribute("orderGoods", orderService.findById(id) );
        return "order/detail";
    }


}
