package com.kurly.demo.web.api;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.service.GoodsServie;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminGoodsApiController {

    private final MessageSource messageSource;
    private final GoodsServie goodsServie;

    @GetMapping("/v1/goods")
    public MappingJacksonValue retrieveAllGoods() {
        List<Goods> goods = goodsServie.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","img","price","description");

        FilterProvider filters = new SimpleFilterProvider().addFilter("GoodsInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(goods);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/v1/goods/{id}")
    public MappingJacksonValue retrieveGoods(@PathVariable Long id) {
        Goods goods = goodsServie.findOne(id);
        if(goods == null){

            throw new CustomNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","img","price","description");

        FilterProvider filters = new SimpleFilterProvider().addFilter("GoodsInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(goods);
        mapping.setFilters(filters);

        return mapping;
    }
}
