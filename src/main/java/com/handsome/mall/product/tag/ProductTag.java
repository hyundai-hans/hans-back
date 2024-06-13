package com.handsome.mall.product.tag;

import lombok.Data;

@Data
public class ProductTag {
  private Long id;
  private Long productId;
  private String body;
}
