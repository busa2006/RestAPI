package com.kurly.demo.service;

import com.kurly.demo.common.Common;
import com.kurly.demo.domain.Goods;
import com.kurly.demo.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServie {

    private final GoodsRepository goodsRepository;
    private final Common common;

    @Transactional(rollbackFor = Exception.class)
    public Goods saveGoods(Goods goods){
        Goods result = null;
        //common.requiredSaveGoods();
        result = goodsRepository.save(goods);
        return result;
    }




    public Goods findOne(Long goodsId){
        return goodsRepository.findOne(goodsId);
    }

    public List<Goods> findAll(){return goodsRepository.findAll();}
    public List<Goods> findPaging(int n){
        return goodsRepository.findPaging(n);
    }

    public void deleteById(Long id) {
        goodsRepository.deleteById(id);
    }
}
