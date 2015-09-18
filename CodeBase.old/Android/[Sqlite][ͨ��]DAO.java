package com.example.db.dao.base;

import java.io.Serializable;
import java.util.List;

import com.example.db.dao.domain.News;

/**
 * ʵ�������ͨ�ýӿ�
 */
public interface DAO<M> {
	/**
	 * ����
	 * @param m   ʵ�����
	 * @return  long  �嵽�˵�x��
	 */
	long insert(M m);
	/**
	 * ɾ��
	 * @param id  Ҫɾ����id��
	 * @return  ɾ����x��
	 */
	int delete(Serializable id); //int long String
	/**
	 * �޸�
	 * @param m  ʵ����󣬶����װ��Ҫ�޸ĵ����ݡ�
	 * @return  �޸���x��
	 */
	int update(M m);
	/**
	 * ��ѯ
	 * @return
	 */
	List<M> findAll();
}
