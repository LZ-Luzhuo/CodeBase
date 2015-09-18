package com.example.db.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ָ���˽�ʵ�������ݿ��б�Ķ�Ӧ��ϵ
 * @author LuZhuo
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableName {
	/**
	 * ���ݿ��б���
	 * @return
	 */
	String value();

}
