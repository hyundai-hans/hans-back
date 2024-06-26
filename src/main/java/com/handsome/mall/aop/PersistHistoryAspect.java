package com.handsome.mall.aop;

import com.handsome.mall.dto.HistoryPostPersistDto;
import com.handsome.mall.dto.ImgDto;
import com.handsome.mall.dto.response.PostDetailResponse;
import com.handsome.mall.exception.PostException;
import com.handsome.mall.handler.PostHistoryManageCommandHandler;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * This is the class which is for persisting the data into historyDB by using Spring AOP
 */

@Aspect
@Component
@RequiredArgsConstructor
public class PersistHistoryAspect {

    private final PostHistoryManageCommandHandler postHistoryManageCommandHandler;

    @Pointcut("@annotation(com.handsome.mall.annotation.PersistHistory)")
    public void persistHistoryMethods() {
    }

    /**
     * *
      * @param joinPoint
     * @param postDetail when the user see the postDetail, persist into historyDB the user history
     *                   which is recently read
     */
    @AfterReturning(pointcut = "persistHistoryMethods()", returning = "postDetail")
    public void afterReturningAdvice(JoinPoint joinPoint, PostDetailResponse postDetail) {

        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[0];
        Long postId = (Long) args[1];

        if(userId!=null){
        String thumbnailImage = postDetail.getImgList().stream().filter(
            ImgDto::getIsThumbnail).findFirst().orElseThrow(() -> {
            throw new PostException("썸네일이 존재하지 않은 제품입니다.");
        }).getImgUrl();

        postHistoryManageCommandHandler.persistHistory(
            HistoryPostPersistDto.builder().memberId(userId).postId(postId)
                .thumbnailImagUrl(thumbnailImage).build());
    }
    }
}

