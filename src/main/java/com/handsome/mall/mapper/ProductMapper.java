package com.handsome.mall.mapper;

import com.handsome.mall.dto.ProductDto;
import com.handsome.mall.entity.primary.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

      ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
   @Mapping(source = "price", target = "productPrice")
    @Mapping(source = "imgUrl", target = "productImg")
    @Mapping(source = "uri", target = "productUrl")
    @Mapping(source = "name", target = "productName")
   ProductDto toProductDTO(Product product);

}
