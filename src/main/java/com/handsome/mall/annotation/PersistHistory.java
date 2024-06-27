package com.handsome.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is the marker annotation for The annotated with this annotation will put the data into HistoryDB
 * @see com.handsome.mall.aop.PersistHistoryAspect This marker annotation is used with aop proxy
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PersistHistory {

}
