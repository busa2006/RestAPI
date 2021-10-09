package com.kurly.demo.web.api;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kurly.demo.common.Common;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.domain.GoodsV2;
import com.kurly.demo.service.GoodsServie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GoodsApiController {

    private final MessageSource messageSource;
    private final GoodsServie goodsServie;
    private final Common common;
    private final String goodsFilterName = "GoodsInfo";
    private final String goodsV2FilterName = "GoodsInfoV2";

    @GetMapping("/v1/goods")
    public MappingJacksonValue retrieveAllGoods() {
        List<Goods> goods = goodsServie.findAll();
        return common.filter(goods,goodsFilterName,"id","name","img","price");
    }

    @PostMapping("/v1/goods")
    public ResponseEntity<Goods> createGoods(@Valid @RequestBody Goods goods) throws Exception {
        Goods savedGoods = goodsServie.saveGoods(goods);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGoods.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/v1/goods/{id}") // URI
//    @GetMapping(value = "/goods/{id}/", params = "version = 1") // PARAMETER
//    @GetMapping(value = "/goods/{id}", headers = "X-API-VERSION=1") // HEADER
//    @GetMapping(value = "/goods/{id}", produces = "application/vnd.company.appv1+json") // MINE
    public MappingJacksonValue retrieveGoods(@PathVariable Long id) {
        Goods goods = goodsServie.findOne(id);
        if(goods == null){

            throw new CustomNotFoundException(messageSource.getMessage("error.notFound",new String[]{id.toString()},Locale.getDefault()));
        }

        EntityModel<Goods> model = EntityModel.of(goods);
        WebMvcLinkBuilder linkTo =
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllGoods());

        model.add(linkTo.withRel("all-users"));

        return common.filter(goods,goodsFilterName,"id","name","img","price");
    }

    @DeleteMapping("/v1/goods/{id}")
    public void deleteGoods(@PathVariable Long id) {

    }

    @GetMapping("/v2/goods/{id}")
    //@GetMapping(value = "/goods/{id}/", params = "version = 2")
    //@GetMapping(value = "/goods/{id}", headers = "X-API-VERSION=2")
    //@GetMapping(value = "/goods/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveGoodsV2(@PathVariable Long id) {
        Goods goods = goodsServie.findOne(id);
        if(goods == null){

            throw new CustomNotFoundException(String.format("ID[%s] not found", id));
        }

        GoodsV2 goodsV2 = new GoodsV2();
        BeanUtils.copyProperties(goods,goodsV2);
        goodsV2.setStorage("상온");

        return common.filter(goodsV2,goodsV2FilterName,"id","name","img","price","storage");
    }



    @GetMapping("/hello")
    public String hello(@RequestHeader(name="Accept-Language",required=false) Locale locale){
        return messageSource.getMessage("greeting.message",null,locale);
    }


}
