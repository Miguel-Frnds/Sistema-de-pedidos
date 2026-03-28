package br.com.miguel.ordem_management.domain.repository;

import br.com.miguel.ordem_management.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
