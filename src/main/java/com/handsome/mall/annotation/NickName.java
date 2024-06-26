package com.handsome.mall.annotation;

import com.handsome.mall.validation.NicknameValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is for checking the nickname validation check which is bound the NickNameValidator.class
 * @see NicknameValidator
 */
@Constraint(validatedBy = NicknameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NickName {
    String message() default "닉네임이 중복되었습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
