package com.handsome.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is the marker annotation for putting the data into historyDB
 * @see com.handsome.mall.aop.PersistHistoryAspect It can be used with proxy
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PersistHistory {

}
