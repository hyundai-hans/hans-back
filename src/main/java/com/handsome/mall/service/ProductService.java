package com.handsome.mall.service;

import com.handsome.mall.dto.ProductSearchedDto;
import com.handsome.mall.entity.primary.Product;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductService<ID extends Object> {
  List<ProductSearchedDto> findProduct(String name);

}
