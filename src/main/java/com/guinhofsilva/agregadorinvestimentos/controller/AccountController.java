package com.guinhofsilva.agregadorinvestimentos.controller;

import com.guinhofsilva.agregadorinvestimentos.Dto.AccountStockResponseDto;
import com.guinhofsilva.agregadorinvestimentos.Dto.AssociateAccountStockDto;
import com.guinhofsilva.agregadorinvestimentos.model.AccountStock;
import com.guinhofsilva.agregadorinvestimentos.service.AccountService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable UUID accountId, @RequestBody AssociateAccountStockDto dto){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> findAllStocks(@PathVariable UUID accountId){
        return ResponseEntity.ok(accountService.findAllStocks(accountId));
    }

}
