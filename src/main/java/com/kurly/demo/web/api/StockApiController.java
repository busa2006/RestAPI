package com.kurly.demo.web.api;

import com.kurly.demo.domain.Stock;
import com.kurly.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockApiController {

    private final StockService stockService;

    @GetMapping("/v1/stocks")
    public List<Stock> retrieveAll(){
        List<Stock> stocks = stockService.findAll();
        return stocks;
    }
}
