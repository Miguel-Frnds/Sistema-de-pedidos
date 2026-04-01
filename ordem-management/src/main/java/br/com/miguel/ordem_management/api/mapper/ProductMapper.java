package br.com.miguel.ordem_management.api.mapper;

import br.com.miguel.ordem_management.api.dto.product.ProductCreateRequestDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductResponseDTO;
import br.com.miguel.ordem_management.domain.entity.Product;

public class ProductMapper {
    public static Product toEntity(ProductCreateRequestDTO dto){
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        return product;
    }

    public static ProductResponseDTO toResponseDTO(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.isActive()
        );
    }
}
