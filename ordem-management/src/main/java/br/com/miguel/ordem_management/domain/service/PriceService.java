package br.com.miguel.ordem_management.domain.service;

import br.com.miguel.ordem_management.api.dto.price.PriceRequestDTO;
import br.com.miguel.ordem_management.api.dto.price.PriceResponseDTO;
import br.com.miguel.ordem_management.api.mapper.PriceMapper;
import br.com.miguel.ordem_management.domain.entity.Price;
import br.com.miguel.ordem_management.domain.entity.Product;
import br.com.miguel.ordem_management.domain.exception.PriceNotFoundException;
import br.com.miguel.ordem_management.domain.exception.SamePriceException;
import br.com.miguel.ordem_management.domain.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ProductService productService;

    public List<PriceResponseDTO> findAllByProductId(Long id){
        productService.findById(id);
        return priceRepository.findAllByProductId(id).stream()
                .map(PriceMapper::toResponseDTO)
                .toList();
    }

    public PriceResponseDTO findByIdAndProductId(Long id, Long productId){
        return PriceMapper.toResponseDTO(getByIdAndProductId(id, productId));
    }

    @Transactional
    public PriceResponseDTO newPrice(PriceRequestDTO priceDTO, Long productId){
        Product product = productService.getById(productId);

        if(priceDTO.value().compareTo(product.getPrice()) == 0) {
            throw new SamePriceException();
        }

        Price price = new Price();
        price.setValue(priceDTO.value());
        price.setProduct(product);

        Price savedPrice = priceRepository.save(price);
        productService.updatePrice(productId, savedPrice.getValue());

        return PriceMapper.toResponseDTO(savedPrice);
    }

    public void registerInitialPrice(PriceRequestDTO priceDTO, Long productId){
        Product product = productService.getById(productId);

        Price price = new Price();
        price.setValue(priceDTO.value());
        price.setProduct(product);

        priceRepository.save(price);
    }

    private Price getByIdAndProductId(Long id, Long productId){
        return priceRepository.findByIdAndProductId(id, productId)
                .orElseThrow(() -> new PriceNotFoundException(id, productId));
    }
}
