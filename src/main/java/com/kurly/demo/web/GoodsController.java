package com.kurly.demo.web;

import com.kurly.demo.domain.Goods;
import com.kurly.demo.service.GoodsServie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsServie goodsServie;

    @GetMapping("/{itemId}")
    public String detail(@PathVariable("itemId") Long goodsId, Model model){
        model.addAttribute("goods", goodsServie.findOne(goodsId));
        return "goods/detail";
    }

}
