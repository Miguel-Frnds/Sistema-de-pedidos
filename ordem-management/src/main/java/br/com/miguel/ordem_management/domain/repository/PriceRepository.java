package br.com.miguel.ordem_management.domain.repository;

import br.com.miguel.ordem_management.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findAllByProductId(Long id);
    Optional<Price> findByIdAndProductId(Long id, Long productId);
}
