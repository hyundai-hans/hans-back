package com.handsome.mall.service;

import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostLike;
import com.handsome.mall.exception.PostException;
import com.handsome.mall.exception.UserException;
import com.handsome.mall.mapper.PostLikeMapper;
import com.handsome.mall.repository.primary.MemberRepository;
import com.handsome.mall.repository.primary.PostLikeRepository;
import com.handsome.mall.repository.primary.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HansPostLikeService implements PostLikeService<Long, Long> {


  private final PostRepository postRepository;
  private final PostLikeRepository postLikesRepository;
  private MemberRepository memberRepository;


  @Override
  public void likesOrUnLikes(Long userId, Long postId) {
    Post post = findPost(postId);
    Member member = findMember(userId);
    Optional<PostLike> postLikeOptional = findPostLikeUserAlreadyLikeIt(member,post);

    if (postLikeOptional.isPresent()) {
      PostLike postLike = postLikeOptional.get();
      boolean updatedStatus = !postLike.getIsLiked();
      PostLikeMapper.INSTANCE.updatePostLike(postLike.getId(), postLike.getMember(),
          postLike.getPost(), updatedStatus);
    } else {
      PostLike newPostLike = PostLike.builder().post(post).member(member).isLiked(true).build();
      postLikesRepository.save(newPostLike);
    }

  }

  private Member findMember(Long userId) {
    return memberRepository.findById(userId).orElseThrow(() -> {
      throw new UserException("존재하지 않은 유저입니다.");
    });
  }

  private Post findPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(() -> {
      throw new PostException("존재하지 않는 게시글입니다.");
    });
  }

  private Optional<PostLike> findPostLikeUserAlreadyLikeIt(Member member,Post post) {
    return postLikesRepository.findByMemberAndPost(member, post);
  }


}
