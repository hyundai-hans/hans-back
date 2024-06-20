package com.handsome.mall.aop;

import com.handsome.mall.dto.HistoryPostPersistDto;
import com.handsome.mall.dto.ImgDto;
import com.handsome.mall.dto.response.PostDetailResponse;
import com.handsome.mall.exception.PostException;
import com.handsome.mall.handler.PostHistoryManageHandler;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PersistHistoryAspect {

    private final PostHistoryManageHandler postHistoryManageHandler;

    @Pointcut("@annotation(com.handsome.mall.annotation.PersistHistory)")
    public void persistHistoryMethods() {
    }

    @AfterReturning(pointcut = "persistHistoryMethods()", returning = "postDetail")
    public void afterReturningAdvice(JoinPoint joinPoint, PostDetailResponse postDetail) {

        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[0];
        Long postId = (Long) args[1];
        String thumbnailImage = postDetail.getImgList().stream().filter(
            ImgDto::getIsThumbnail).findFirst().orElseThrow(() -> {
            throw new PostException("썸네일이 존재하지 않은 제품입니다.");
        }).getImgUrl();

        postHistoryManageHandler.persistHistory(
            HistoryPostPersistDto.builder().memberId(userId).postId(postId)
                .thumbnailImagUrl(thumbnailImage).build());
    }
}

