package com.handsome.mall.http.controller;

import com.handsome.mall.dto.ProductSearchedDto;
import com.handsome.mall.http.message.SuccessResponse;
import com.handsome.mall.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductRestController<PID> {

  private final ProductService<PID> productService;

  @GetMapping()
  public ResponseEntity<SuccessResponse<List<ProductSearchedDto>>> getProduct(@RequestParam("product_name") String productName){
    List<ProductSearchedDto> response = productService.findProduct(productName);
    return ResponseEntity.ok(SuccessResponse.<List<ProductSearchedDto>>builder().data(response).status(
        HttpStatus.OK.toString()).message("상품 이미지 반환 성공").build());
  }
}
