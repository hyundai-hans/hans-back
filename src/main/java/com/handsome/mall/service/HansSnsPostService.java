package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.ImgDto;
import com.handsome.mall.dto.ProductDto;
import com.handsome.mall.dto.TagDto;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.dto.response.PostDetailResponse;
import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import com.handsome.mall.entity.primary.PostTag;
import com.handsome.mall.entity.primary.Product;
import com.handsome.mall.exception.PostException;
import com.handsome.mall.exception.ProductException;
import com.handsome.mall.exception.UserException;
import com.handsome.mall.mapper.PostImgMapper;
import com.handsome.mall.mapper.PostMapper;
import com.handsome.mall.mapper.ProductMapper;
import com.handsome.mall.mapper.TagMapper;
import com.handsome.mall.repository.primary.MemberRepository;
import com.handsome.mall.repository.primary.PostImgRepository;
import com.handsome.mall.repository.primary.PostRepository;
import com.handsome.mall.repository.primary.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Service
public class HansSnsPostService implements PostService<Long, Long> {

  private final PostImgRepository postImgRepository;
  private final ProductRepository productRepository;
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  @Transactional("primaryTransactionManager")
  @Override
  public void createPost(Long userId, CreatePostDto createPostDto) {
    Product product = findProduct(createPostDto.getProductName());
    Member member = getMember(userId);
    List<String> tagBodyList = createPostDto.getTagList();
    List<String> postImgList = createPostDto.getImgUrl();

    List<PostImg> postImgEntityList = PostImgMapper.INSTANCE.mapImgUrlsToPostImages(postImgList);
    List<PostTag> postTagList = TagMapper.INSTANCE.mapToPostTags(tagBodyList);

    Post post = PostMapper.INSTANCE.createPostDtoToPost(createPostDto, product, postTagList,member,postImgEntityList);
    setParentHelper(postImgEntityList,postTagList,post);

    postImgRepository.saveAll(postImgEntityList);
    postRepository.save(post);

  }

  private void setParentHelper(List<PostImg> postImgList, List<PostTag> postTagList, Post post){
    for(PostImg img : postImgList){
      img.setPost(post);
    }

    for(PostTag tag : postTagList){
      tag.setPost(post);
    }
  }




  @Override
  public void deletePost(Long userId, Long postId) {
    Post post = findPost(userId, postId);
    postRepository.delete(post);
  }

  /**
   * *
   * @thumbNailImgUrl Optional.get() won't be thrown an exception there is thumbnail img essentially
   *
   */
  @Transactional("primaryTransactionManager")
  @Override
    public List<FindPostResponse> findPostByTitle(String title,Pageable pageable) {

    Page<Post> postPage = postRepository.findByTitleContainingOrderByLikes(title, pageable);

        List<Post> postList = postPage.getContent();

        String thumbNailImgUrl = postList.isEmpty() ? null : postList.get(0).getPostImages().stream()
                .filter(PostImg::getIsThumbnail)
                .findFirst()
                .map(PostImg::getImgUrl)
                .orElse(null);
        return PostMapper.INSTANCE.postsToFindPostResponses(postList, thumbNailImgUrl);
    }

  @Transactional("primaryTransactionManager")
  @Override
  public PostDetailResponse findPostById(Long postId ) {

    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new PostException("존재하지 않는 상품입니다."));

    List<TagDto> tagDtoList = post.getPostTags().stream()
        .map(TagMapper.INSTANCE::postTagToTagDto)
        .collect(Collectors.toList());

    List<ImgDto> imgDtoList = post.getPostImages().stream()
        .map(PostImgMapper.INSTANCE::postImgToImgDto)
        .collect(Collectors.toList());

    ProductDto productDto = ProductMapper.INSTANCE.toProductDTO(post.getProduct());

    return PostMapper.INSTANCE.toPostDetailResponseDto(post, productDto, tagDtoList,
        imgDtoList);
  }

  @Transactional("primaryTransactionManager")
  @Override
  public void updatePost(Long userId, UpdatePostDto updatePostDto) {
    Post post = findPost(userId, updatePostDto.getPostId());

    List<PostTag> updatedTagList = post.getPostTags().stream()
        .map(postTag -> {
          Optional<TagDto> matchingTagDto = updatePostDto.getTagList().stream()
              .filter(tagDto -> tagDto.getTagId().equals(postTag.getId()))
              .findFirst();
          if (matchingTagDto.isPresent()) {
            return PostMapper.INSTANCE.updateThroughTagDto(matchingTagDto.get(), postTag);
          }
          return postTag;
        })
            .collect(Collectors.toList());

    List<PostImg> updatedPostImgList = post.getPostImages().stream()
        .map(postImg -> {
          Optional<ImgDto> matchingImgDto = updatePostDto.getImgList().stream()
              .filter(imgDto -> imgDto.getImgId().equals(postImg.getId()))
              .findFirst();
          if (matchingImgDto.isPresent()) {
            return PostImgMapper.INSTANCE.imgDtoToPostImg(matchingImgDto.get(), postImg);
          }
          return postImg;
        })
        .collect(Collectors.toList());

    post.setPostImages(updatedPostImgList);
    post.setPostTags(updatedTagList);
    post.setBody(updatePostDto.getBody());
    post.setTitle(updatePostDto.getTitle());
    postRepository.save(post);
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

  private Member getMember(Long id) {
    return memberRepository.findById(id).orElseThrow(() -> {
      throw new UserException("존재 하지 않는 유저입니다.");
    });
  }

}
