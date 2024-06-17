package com.handsome.mall.repository.primary;

import com.handsome.mall.entity.primary.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  public Optional<Product> findByName(String name);
}
