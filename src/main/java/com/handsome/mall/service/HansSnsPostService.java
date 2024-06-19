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
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    List<ImgDto> imgDtoList = updatePostDto.getImgList();
    List<TagDto> tagDtoList = updatePostDto.getTagList();

    List<PostTag> updatedTagList = bindUpdatedPostTagAlreadyExistFromDto(tagDtoList, post);
    List<PostImg> updatedPostImgList = bindUpdatedPostImageAlreadyExistFromDto(imgDtoList, post);

    List<PostTag> newPostTagList = bindNewPostTagsFromDto(tagDtoList, post);
    List<PostImg> newPostImgList = bindNewPostImagesFromDto(imgDtoList, post);

    checkThumbnailValidation(updatedPostImgList);

    updatedTagList.addAll(newPostTagList);
    updatedPostImgList.addAll(newPostImgList);

    post.setPostImages(updatedPostImgList);
    post.setPostTags(updatedTagList);
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

  private List<PostTag> bindUpdatedPostTagAlreadyExistFromDto(List<TagDto> tagDtoList, Post post) {
    Map<Long, TagDto> tagDtoMap = tagDtoList.stream()
        .filter(tagDto -> tagDto.getTagId() != null)
        .collect(Collectors.toMap(TagDto::getTagId, Function.identity()));

    return post.getPostTags().stream()
        .filter(postTag -> tagDtoMap.containsKey(postTag.getId()))
        .map(postTag -> {
          TagDto idMatchedTagDto = tagDtoMap.get(postTag.getId());
          return TagMapper.INSTANCE.updateThroughTagDto(idMatchedTagDto,post);
        })
        .collect(Collectors.toList());
  }

  private List<PostImg> bindUpdatedPostImageAlreadyExistFromDto(List<ImgDto> imgDtoList,
      Post post) {
    Map<Long, ImgDto> imgDtoMap = imgDtoList.stream()
        .filter(imgDto -> imgDto.getImgId() != null)
        .collect(Collectors.toMap(ImgDto::getImgId, Function.identity()));

    return post.getPostImages().stream()
        .filter(postImg -> imgDtoMap.containsKey(postImg.getId()))
        .map(postImg -> {
          ImgDto idMatchedImgDto = imgDtoMap.get(postImg.getId());
        return  PostImgMapper.INSTANCE.imgDtoToPostImg(idMatchedImgDto,post);
        })
        .collect(Collectors.toList());
  }

  private List<PostTag> bindNewPostTagsFromDto(List<TagDto> tagDtoList, Post post) {
    return tagDtoList.stream()
        .filter(tagDto -> tagDto.getTagId() == null)
        .map(tagDto -> {
          PostTag newTag = TagMapper.INSTANCE.mapToPostTagFromDto(tagDto);
          newTag.setPost(post);
          return newTag;
        })
        .collect(Collectors.toList());
  }

  private List<PostImg> bindNewPostImagesFromDto(List<ImgDto> imgDtoList, Post post) {
    return imgDtoList.stream()
        .filter(imgDto -> imgDto.getImgId() == null)
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
