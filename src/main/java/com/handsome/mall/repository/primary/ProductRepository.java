package com.handsome.mall.repository.primary;

import com.handsome.mall.entity.primary.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
  List<Product> findByNameContaining(@Param("name") String name);
}
