package com.handsome.mall.service;

import com.handsome.mall.dto.ProductSearchedDto;
import com.handsome.mall.entity.primary.Product;
import com.handsome.mall.mapper.ProductMapper;
import com.handsome.mall.repository.primary.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HansProductService implements ProductService<Long> {

  private final ProductRepository productRepository;
  @Override
  public List<ProductSearchedDto> findProduct(String name) {
    return productRepository.findByNameContaining(name).stream().map(
        ProductMapper.INSTANCE::toProductSearchDto).collect(Collectors.toList());
  }
}
