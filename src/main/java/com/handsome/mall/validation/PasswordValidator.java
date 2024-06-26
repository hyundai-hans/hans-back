package com.handsome.mall.validation;

import com.handsome.mall.annotation.Password;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * This is the ConstraintValidator which is bound with Password annotation
 * @see Password
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$";

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches(PASSWORD_PATTERN);
    }
}
