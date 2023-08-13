package com.jsh.erp.service.serialNumber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jsh.erp.service.ResourceInfo;

/**
 * Description
 *
 * @Author: jishenghua
 * @Date: 2019/1/21 16:33
 */
@ResourceInfo(value = "serialNumber")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerialNumberResource {
}
