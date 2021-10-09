package com.kurly.demo.service;

import com.kurly.demo.domain.*;
import com.kurly.demo.repository.CartRepository;
import com.kurly.demo.repository.GoodsRepository;
import com.kurly.demo.repository.StockRepository;
import com.kurly.demo.repository.UserRepository;
import com.kurly.demo.web.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

}
