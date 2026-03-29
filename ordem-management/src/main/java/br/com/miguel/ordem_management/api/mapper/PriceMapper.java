package br.com.miguel.ordem_management.api.mapper;

import br.com.miguel.ordem_management.api.dto.price.PriceResponseDTO;
import br.com.miguel.ordem_management.domain.entity.Price;

public class PriceMapper {
    public static PriceResponseDTO toResponseDTO(Price price){
        return new PriceResponseDTO(
                price.getId(),
                price.getValue(),
                price.getCreatedAt(),
                price.getProduct().getId()
        );
    }
}
