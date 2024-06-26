package com.handsome.mall.validation;

import com.handsome.mall.annotation.NickName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This is the ConstraintValidator which is bound with NickName annotation
 * @see NickName
 */
public class NicknameValidator implements ConstraintValidator<NickName, String> {

    private static final String NICKNAME_PATTERN = "^.{1,}$";

    @Override
    public void initialize(NickName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        return nickname != null && nickname.matches(NICKNAME_PATTERN);
    }
}
