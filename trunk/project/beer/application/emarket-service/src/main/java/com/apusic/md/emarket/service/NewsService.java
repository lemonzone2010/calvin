package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.News;

public interface NewsService {

	/**
	 *根据标题分页查询公告
	 * @param name
	 * @return
	 */
	 Page<News> findNews4Page(GenericQueryObject queryObject);

	 /**
	  * 得到所有的公告 ，分页显示
	  * @return
	  */
	 Page<News> getAllNews();

	 /**
	  * 根据id 删除 公告
	  * @param id
	  */
	 void deleteNewsById(int id);

	 /**
	  *更新 公告
	  * @param originalNews
	  */
	void updateNews(News news);

	/**
	 * 创建 公告
	 * @param news
	 */
	void createNews(News news);

	/**
	 * 获取首页中显示的公告
	 * @return
	 */
	List<News> getTopNews();
}
