package com.example.db.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ָ���˽�ʵ����ֶ������ݿ�����еĶ�Ӧ��ϵ
 * @author LuZhuo
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * ���ݿ��б���
	 * @return
	 */
	String value();

}
