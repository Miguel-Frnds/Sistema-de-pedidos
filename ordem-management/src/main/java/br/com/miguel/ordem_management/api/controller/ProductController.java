package br.com.miguel.ordem_management.api.controller;

import br.com.miguel.ordem_management.api.dto.product.ProductCreateRequestDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductResponseDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductStockRequestDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductUpdateRequestDTO;
import br.com.miguel.ordem_management.domain.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll(){
        List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){
        ProductResponseDTO productFound = productService.findById(id);
        return ResponseEntity.ok().body(productFound);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductCreateRequestDTO requestDTO){
        ProductResponseDTO product = productService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDTO requestDTO){
        ProductResponseDTO product = productService.update(id, requestDTO);
        return ResponseEntity.ok().body(product);
    }

    @PatchMapping("/{id}/stock/add")
    public ResponseEntity<ProductResponseDTO> addStock(@PathVariable Long id, @Valid @RequestBody ProductStockRequestDTO requestDTO){
        ProductResponseDTO product = productService.addStock(id, requestDTO);
        return ResponseEntity.ok().body(product);
    }

    @PatchMapping("/{id}/stock/remove")
    public ResponseEntity<ProductResponseDTO> removeStock(@PathVariable Long id, @Valid @RequestBody ProductStockRequestDTO requestDTO){
        ProductResponseDTO product = productService.removeStock(id, requestDTO);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
