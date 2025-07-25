package com.guinhofsilva.agregadorinvestimentos.controller;

import com.guinhofsilva.agregadorinvestimentos.dto.CreateStockDto;
import com.guinhofsilva.agregadorinvestimentos.model.Stock;
import com.guinhofsilva.agregadorinvestimentos.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody CreateStockDto stockDto){
        return ResponseEntity.created(URI.create(stockService.createStock(stockDto).toString())).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Stock> getStock(@PathVariable String id){
        return ResponseEntity.ok(stockService.findById(id));
    }







}
