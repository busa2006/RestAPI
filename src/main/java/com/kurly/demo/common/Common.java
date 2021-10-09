package com.kurly.demo.common;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.repository.GoodsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Common {
    private final GoodsRepository goodsRepository;

    public <T> MappingJacksonValue filter(List<T> goods, String filterName, String ...exceptColumn){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(exceptColumn);

        FilterProvider filters = new SimpleFilterProvider().addFilter(filterName,filter);

        MappingJacksonValue mapping = new MappingJacksonValue(goods);
        mapping.setFilters(filters);
        return mapping;
    }

    public <T> MappingJacksonValue filter(T goods, String filterName, String ...exceptColumn){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept(exceptColumn);

        FilterProvider filters = new SimpleFilterProvider().addFilter(filterName,filter);

        MappingJacksonValue mapping = new MappingJacksonValue(goods);
        mapping.setFilters(filters);
        return mapping;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public void requiredSaveGoods()  {
        try {
            Goods goods = Goods.builder().name("test").img("test").price(0).description("test").build();
            goodsRepository.save(goods);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

    }

}
