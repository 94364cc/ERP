package com.jsh.erp.service.orgaUserRel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jsh.erp.service.ResourceInfo;

/**
 * Description
 *  机构用户关系
 * @Author: cjl
 * @Date: 2019/3/11 18:11
 */
@ResourceInfo(value = "orgaUserRel")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrgaUserRelResource {

}
