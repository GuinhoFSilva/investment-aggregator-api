package com.guinhofsilva.agregadorinvestimentos.mapper;

import com.guinhofsilva.agregadorinvestimentos.dto.CreateStockDto;
import com.guinhofsilva.agregadorinvestimentos.model.Stock;

public class StockMapper {
    public static Stock createStock(CreateStockDto stockDto){
        return new Stock(stockDto.stockId(), stockDto.description());
    }
}
