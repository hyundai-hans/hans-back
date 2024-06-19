package com.handsome.mall.service;

import com.handsome.mall.dto.ProductSearchedDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
  List<ProductSearchedDto> findProduct(String name);

}
