package br.com.miguel.ordem_management.api.controller;

import br.com.miguel.ordem_management.api.dto.price.PriceRequestDTO;
import br.com.miguel.ordem_management.api.dto.price.PriceResponseDTO;
import br.com.miguel.ordem_management.domain.service.PriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{productId}/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping
    public ResponseEntity<List<PriceResponseDTO>> findAllByProduct(@PathVariable Long productId){
        List<PriceResponseDTO> prices = priceService.findAllByProductId(productId);
        return ResponseEntity.ok().body(prices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceResponseDTO> findByIdAndProductId(@PathVariable Long id, @PathVariable Long productId){
        PriceResponseDTO price = priceService.findByIdAndProductId(id, productId);
        return ResponseEntity.ok().body(price);
    }

    @PostMapping
    public ResponseEntity<PriceResponseDTO> newPrice(@RequestBody @Valid PriceRequestDTO requestDTO, @PathVariable Long productId){
        PriceResponseDTO price = priceService.newPrice(requestDTO, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(price);
    }
}
