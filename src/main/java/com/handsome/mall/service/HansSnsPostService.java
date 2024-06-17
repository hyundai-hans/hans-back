package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import com.handsome.mall.entity.primary.PostTag;
import com.handsome.mall.entity.primary.Product;
import com.handsome.mall.exception.PostException;
import com.handsome.mall.exception.ProductException;
import com.handsome.mall.mapper.PostImgMapper;
import com.handsome.mall.mapper.PostMapper;
import com.handsome.mall.mapper.TagMapper;
import com.handsome.mall.repository.primary.PostRepository;
import com.handsome.mall.repository.primary.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    Post post = PostMapper.INSTANCE.createPostDtoToPost(createPostDto, product, postTagList,
        postImgEntityList);

    postRepository.save(post);
  }

  @Override
  public void deletePost(Long userId, Long postId) {
    Post post = findPost(userId, postId);
    postRepository.delete(post);
  }

  /**
   * *
   * @thumbNailImgUrl Optional.get() won't be thrown an exception there is thumbnail img essentially
   * @return
   */
  @Transactional
  @Override
  public List<FindPostResponse> findPost(String postName) {
    List<Post> postList = postRepository.findByTitleLike(postName);
    String thumbNailImgUrl = postList.get(0).getPostImages().stream().filter(
        PostImg::getIsThumbnail).findFirst().get().getImgUrl();
    return PostMapper.INSTANCE.postsToFindPostResponses(postList,thumbNailImgUrl);


  }

  @Override
  public void updatePost(Long userId, UpdatePostDto updatePostDto) {
    Post post = findPost(userId, updatePostDto.getPostId());

    String title = updatePostDto.getTitle();
    String body = updatePostDto.getBody();
    List<PostTag> tagList = updatePostDto.getTagList().stream()
        .map(TagMapper.INSTANCE::tagDtoToPostTag)
        .collect(Collectors.toList());
    List<PostImg> postImgList = updatePostDto.getImgList()
        .stream()
        .map(PostImgMapper.INSTANCE::imgDtoToPostImg)
        .collect(Collectors.toList());

   Post updatedPost =  PostMapper.INSTANCE.updatePostDtoToPost(title,body,tagList,postImgList);
   postRepository.save(updatedPost);

  }

  private Product findProduct(String productName) {
    return productRepository.findByName(productName).orElseThrow(() -> {
      throw new ProductException("존재 하지 않은 상품입니다.");
    });
  }

  private Post findPost(Long userId, Long productId) {
    return postRepository.findByMemberIdAndProductId(userId, productId).orElseThrow(() -> {
      throw new PostException("해당 유저가 쓴 글이 아닙니다.");
    });
  }

}
