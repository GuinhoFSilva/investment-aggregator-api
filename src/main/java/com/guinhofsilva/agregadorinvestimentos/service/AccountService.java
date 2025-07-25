package com.guinhofsilva.agregadorinvestimentos.service;

import com.guinhofsilva.agregadorinvestimentos.client.BrapiClient;
import com.guinhofsilva.agregadorinvestimentos.dto.AccountStockResponseDto;
import com.guinhofsilva.agregadorinvestimentos.dto.AssociateAccountStockDto;
import com.guinhofsilva.agregadorinvestimentos.dto.BrapiResponseDto;
import com.guinhofsilva.agregadorinvestimentos.exceptions.ResourceNotFoundException;
import com.guinhofsilva.agregadorinvestimentos.model.Account;
import com.guinhofsilva.agregadorinvestimentos.model.AccountStock;
import com.guinhofsilva.agregadorinvestimentos.model.AccountStockId;
import com.guinhofsilva.agregadorinvestimentos.model.Stock;
import com.guinhofsilva.agregadorinvestimentos.repository.AccountRepository;
import com.guinhofsilva.agregadorinvestimentos.repository.AccountStockRepository;
import com.guinhofsilva.agregadorinvestimentos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Value("#{environment.TOKEN}")
    private String token;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }

    public AccountStockResponseDto associateStock(UUID accountId, AssociateAccountStockDto dto) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account not found!"));

        Stock stock = stockRepository.findById(dto.stockId()).orElseThrow(() -> new ResourceNotFoundException("Stock not found!"));

        AccountStockId id = new AccountStockId(account.getAccount_id(), stock.getStock_id());

        AccountStock accountStock = new AccountStock(id, account, stock, dto.quantity());

        AccountStock accountStockToSave = accountStockRepository.save(accountStock);

        return new AccountStockResponseDto(stock.getStock_id(), accountStockToSave.getQuantity(), getTotal(stock.getStock_id(), accountStockToSave.getQuantity()));
    }

    public List<AccountStockResponseDto> findAllStocks(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account not found!"));

        return account.getAccountStocks().stream()
                .map(acStock -> new AccountStockResponseDto(acStock.getStock().getStock_id(), acStock.getQuantity(), getTotal(acStock.getStock().getStock_id(), acStock.getQuantity())))
                .toList();
    }

    private Double getTotal(String stockId, Integer quantity){
        BrapiResponseDto response = brapiClient.getQuote(token, stockId);

        Double price = response.results().getFirst().regularMarketPrice();

        return quantity * price;
    }


}
