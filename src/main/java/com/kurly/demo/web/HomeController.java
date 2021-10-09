package com.kurly.demo.web;

import com.kurly.demo.config.auth.LoginUser;
import com.kurly.demo.config.auth.dto.SessionUser;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.service.GoodsServie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GoodsServie goodsServie;

    @RequestMapping("/")
    public String home( @LoginUser SessionUser user,Model model){

        if(user != null){
            model.addAttribute("username",user.getName());
        }
//
//        Goods goods = new Goods();
//        goods.setImg("/img/단호박경단.PNG");
//        goods.setName("[유기방아] 단호박경단");
//        goods.setDesc("단호박과 팥앙금의 달콤한 만남");
//        goods.setPrice(4500);
//        goodsServie.saveGoods(goods);
//
//        Goods goods4 = new Goods();
//        goods4.setImg("/img/새우짱.PNG");
//        goods4.setName("[우리밀] 새우짱");
//        goods4.setDesc("싱싱하고 짭짤한 바다의 풍미가 더해진 새우짱");
//        goods4.setPrice(1400);
//        goodsServie.saveGoods(goods4);
//
//        Goods goods2 = new Goods();
//        goods2.setImg("/img/가루.PNG");
//        goods2.setName("[우리밀] 부침가루&튀김가루");
//        goods2.setDesc("쫄깃하고 바삭하게");
//        goods2.setPrice(3000);
//        goodsServie.saveGoods(goods2);
//
//        Goods goods3 = new Goods();
//        goods3.setImg("/img/요플레.PNG");
//        goods3.setName("[상하목장] 유기농 요구르트 플레인");
//        goods3.setDesc("100%유기농 우유와 유산균만으로 만든 자연 그대로의 플레인 요구르트");
//        goods3.setPrice(1400);
//        goodsServie.saveGoods(goods3);

        model.addAttribute("goods",goodsServie.findPaging(4));

        return "home";
    }




}
