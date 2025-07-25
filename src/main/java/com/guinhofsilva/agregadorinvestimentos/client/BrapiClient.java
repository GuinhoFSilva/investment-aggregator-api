package com.guinhofsilva.agregadorinvestimentos.client;

import com.guinhofsilva.agregadorinvestimentos.dto.BrapiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "BrapiCLient", url = "https://brapi.dev")
public interface BrapiClient {
    @GetMapping(value = "/api/quote/{stockId}")
    BrapiResponseDto getQuote(@RequestParam("token") String token, @PathVariable String stockId);
}
