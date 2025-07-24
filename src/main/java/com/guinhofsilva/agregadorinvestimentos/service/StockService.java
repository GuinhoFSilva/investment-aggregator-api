package com.guinhofsilva.agregadorinvestimentos.service;

import com.guinhofsilva.agregadorinvestimentos.Dto.CreateStockDto;
import com.guinhofsilva.agregadorinvestimentos.exceptions.ResourceNotFoundException;
import com.guinhofsilva.agregadorinvestimentos.mapper.StockMapper;
import com.guinhofsilva.agregadorinvestimentos.model.Stock;
import com.guinhofsilva.agregadorinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock createStock(CreateStockDto stockDto) {
        Stock stock = StockMapper.createStock(stockDto);
        return stockRepository.save(stock);
    }

    public Stock findById(String id) {
        Optional<Stock> optStock = stockRepository.findById(id);

        if (optStock.isEmpty()) {
            throw new ResourceNotFoundException("Stock not found!");
        } else {
            return optStock.get();
        }

    }


}
