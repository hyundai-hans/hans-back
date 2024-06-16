package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import com.handsome.mall.entity.primary.PostTag;
import com.handsome.mall.entity.primary.Product;
import com.handsome.mall.exception.ProductException;
import com.handsome.mall.mapper.PostImgMapper;
import com.handsome.mall.mapper.PostMapper;
import com.handsome.mall.mapper.TagMapper;
import com.handsome.mall.repository.primary.PostRepository;
import com.handsome.mall.repository.primary.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HansSnsPostService implements PostService<Long, Long> {

  private final ProductRepository productRepository;
  private final PostRepository postRepository;

  @Override
  public void createPost(Long userId, CreatePostDto createPostDto) {
    Product product = findProduct(createPostDto.getProductName());
    List<String> tagBodyList = createPostDto.getTagList();
    List<String> postImgList = createPostDto.getImgUrl();

    List<PostTag> postTagList = TagMapper.INSTANCE.mapToPostTags(tagBodyList);
    List<PostImg> postImgEntityList = PostImgMapper.INSTANCE.mapImgUrlsToPostImages(postImgList);

    Post post = PostMapper.INSTANCE.createPostDtoToPost(createPostDto,product,postTagList,postImgEntityList);

    postRepository.save(post);
  }

  private Product findProduct(String productName) {
    return productRepository.findByName(productName).orElseThrow(() -> {
      throw new ProductException("존재 하지 않은 상품입니다.");
    });
  }

}
