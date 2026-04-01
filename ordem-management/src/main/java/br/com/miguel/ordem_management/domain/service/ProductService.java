package br.com.miguel.ordem_management.domain.service;

import br.com.miguel.ordem_management.api.dto.product.ProductCreateRequestDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductResponseDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductStockRequestDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductUpdateRequestDTO;
import br.com.miguel.ordem_management.api.mapper.ProductMapper;
import br.com.miguel.ordem_management.domain.entity.Product;
import br.com.miguel.ordem_management.domain.exception.InsufficientStockException;
import br.com.miguel.ordem_management.domain.exception.ProductAlreadyActiveException;
import br.com.miguel.ordem_management.domain.exception.ProductInactiveException;
import br.com.miguel.ordem_management.domain.exception.ProductNotFoundException;
import br.com.miguel.ordem_management.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDTO> findAll(){
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findById(Long id){
        return ProductMapper.toResponseDTO(getById(id));
    }

    public ProductResponseDTO save(ProductCreateRequestDTO product){
        Product createProduct = ProductMapper.toEntity(product);
        return ProductMapper.toResponseDTO(productRepository.save(createProduct));
    }

    public ProductResponseDTO update(Long id, ProductUpdateRequestDTO request){
        Product foundProduct = getActiveById(id);

        if(request.name() != null && !request.name().isBlank()){
            foundProduct.setName(request.name());
        }
        if(request.description() != null && !request.description().isBlank()){
            foundProduct.setDescription(request.description());
        }

        return ProductMapper.toResponseDTO(productRepository.save(foundProduct));
    }

    public void updatePrice(Long id, BigDecimal value){
        Product foundProduct = getActiveById(id);
        foundProduct.setPrice(value);
        productRepository.save(foundProduct);
    }

    public ProductResponseDTO addStock(Long id, ProductStockRequestDTO request){
        Product foundProduct = getActiveById(id);
        foundProduct.setQuantity(foundProduct.getQuantity() + request.quantity());
        return ProductMapper.toResponseDTO(productRepository.save(foundProduct));
    }

    public ProductResponseDTO removeStock(Long id, ProductStockRequestDTO request){
        Product foundProduct = getActiveById(id);

        if(foundProduct.getQuantity() < request.quantity()){
            throw new InsufficientStockException(foundProduct.getName());
        }

        foundProduct.setQuantity(foundProduct.getQuantity() - request.quantity());
        return ProductMapper.toResponseDTO(productRepository.save(foundProduct));
    }

    public void activate(Long id){
        Product foundProduct = getById(id);
        if(foundProduct.isActive()) {
            throw new ProductAlreadyActiveException(id);
        }
        foundProduct.setActive(true);
        productRepository.save(foundProduct);
    }

    public void deactivate(Long id){
        Product foundProduct = getActiveById(id);
        foundProduct.setActive(false);
        productRepository.save(foundProduct);
    }

    public void delete(Long id){
        Product foundProduct = getById(id);
        productRepository.delete(foundProduct);
    }

    public Product getById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private Product getActiveById(Long id){
        Product productFound = getById(id);
        if(!productFound.isActive()) {
            throw new ProductInactiveException(id);
        }
        return productFound;
    }
}
