package com.example.db.dao.base;

import java.io.Serializable;
import java.util.List;

import com.example.db.dao.domain.News;

/**
 * 实体操作的通用接口
 */
public interface DAO<M> {
	/**
	 * 增加
	 * @param m   实体对象
	 * @return  long  插到了第x行
	 */
	long insert(M m);
	/**
	 * 删除
	 * @param id  要删除的id号
	 * @return  删除了x行
	 */
	int delete(Serializable id); //int long String
	/**
	 * 修改
	 * @param m  实体对象，对象封装了要修改的内容。
	 * @return  修改了x行
	 */
	int update(M m);
	/**
	 * 查询
	 * @return
	 */
	List<M> findAll();
}
