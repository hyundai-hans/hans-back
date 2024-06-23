package com.handsome.mall.service;

import com.handsome.mall.annotation.PersistHistory;
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
import com.handsome.mall.repository.primary.PostLikeRepository;
import com.handsome.mall.repository.primary.PostRepository;
import com.handsome.mall.repository.primary.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HansSnsPostService implements PostService {

  private final PostImgRepository postImgRepository;
  private final ProductRepository productRepository;
  private final PostRepository postRepository;
  private final PostLikeRepository postLikeRepository;
  private final MemberRepository memberRepository;

  @Transactional("primaryTransactionManager")
  @Override
  public void createPost(Long userId, CreatePostDto createPostDto) {
    Product product = findProduct(createPostDto.getProductName());
    Member member = getMember(userId);
    List<String> tagBodyList = createPostDto.getTagList();
    List<String> postImgList = createPostDto.getImgUrl();

    List<PostImg> postImgEntityList = PostImgMapper.INSTANCE.mapImgUrlsToPostImages(postImgList);
    List<PostTag> postTagList = TagMapper.INSTANCE.mapToPostTagsFromBody(tagBodyList);

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
    public List<FindPostResponse> findPostByTitle(String title, String tagName, Pageable pageable) {
        Page<Post> postPage = postRepository.findByTitleContainingAndTagBody(title, tagName, pageable);
        List<Post> postList = postPage.getContent();
        return postList.stream().map(this::convertToFindPostResponse).collect(Collectors.toList());
    }

    private FindPostResponse convertToFindPostResponse(Post post) {
        String thumbnailImgUrl = post.getPostImages().stream()
            .filter(PostImg::getIsThumbnail)
            .map(PostImg::getImgUrl)
            .findFirst()
            .orElse(null);

        return new FindPostResponse(
            post.getId(),
            post.getTitle(),
            post.getMember().getNickname(),
            thumbnailImgUrl,
            post.getCreatedAt()
        );
    }


  @Transactional("primaryTransactionManager")
  @PersistHistory
  @Override
  public PostDetailResponse findPostById(Long userId, Long postId) {

    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new PostException("존재하지 않는 상품입니다."));

    List<TagDto> tagDtoList = post.getPostTags().stream()
        .map(TagMapper.INSTANCE::postTagToTagDto)
        .collect(Collectors.toList());

    List<ImgDto> imgDtoList = post.getPostImages().stream()
        .map(PostImgMapper.INSTANCE::postImgToImgDto)
        .collect(Collectors.toList());

    Boolean isUserLikeThisPost = isLoginUserLikeThisPost(userId, post);

    ProductDto productDto = ProductMapper.INSTANCE.toProductDTO(post.getProduct());

    return PostMapper.INSTANCE.toPostDetailResponseDto(post, productDto, tagDtoList,
        imgDtoList, isUserLikeThisPost);
  }

  private Boolean isLoginUserLikeThisPost(Long userId, Post post) {
    return userId != null && postLikeRepository.findByMemberIdAndPost(userId, post).isPresent();
  }


  @Transactional("primaryTransactionManager")
  @Override
  public void updatePost(Long userId, UpdatePostDto updatePostDto) {

    Post post = findPost(userId, updatePostDto.getPostId());

    List<ImgDto> imgDtoList = updatePostDto.getImgList();
    List<TagDto> tagDtoList = updatePostDto.getTagList();

    List<PostTag> updatedTagList = getPostTagFromDto(tagDtoList, post);
    List<PostImg> updatedPostImgList = getPostImgFromDto(imgDtoList, post);


    checkThumbnailValidation(updatedPostImgList);



    post.getPostImages().clear();
    post.getPostImages().addAll(updatedPostImgList);

    post.getPostTags().clear();
    post.getPostTags().addAll(updatedTagList);

    post.setBody(updatePostDto.getBody());
    post.setTitle(updatePostDto.getTitle());

    postRepository.save(post);
  }

  private void checkThumbnailValidation(List<PostImg> postImgList) {
    long counter = postImgList.stream().filter(PostImg::getIsThumbnail).count();
    if (counter != 1) {
      throw new PostException("하나의 대표이미지를 설정해주세요");
    }
  }


  private List<PostTag> getPostTagFromDto(List<TagDto> tagDtoList, Post post) {
    return tagDtoList.stream()
        .map(tagDto -> {
          return TagMapper.INSTANCE.mapToPostTagFromDto(tagDto,post);
        })
        .collect(Collectors.toList());
  }

  private List<PostImg>
  getPostImgFromDto(List<ImgDto> imgDtoList, Post post) {
    return imgDtoList.stream()
        .map(imgDto -> {
            return PostImgMapper.INSTANCE.imgDtoToPostImg(imgDto, post);
        })
        .collect(Collectors.toList());
  }

  private Product findProduct(String productName) {
    return productRepository.findByName(productName).orElseThrow(() -> {
      throw new ProductException("존재 하지 않은 상품입니다.");
    });
  }


  private Post findPost(Long memberId, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> {
      throw new PostException("존재하지 않은 글입니다.");
    });
    if (!post.getMember().getId().equals(memberId)) {
      throw new PostException("해당 유저가 쓴 글이 아닙니다.");
    }
    return post;
  }

  private Member getMember(Long id) {
    return memberRepository.findById(id).orElseThrow(() -> {
      throw new UserException("존재 하지 않는 유저입니다.");
    });
  }

}
