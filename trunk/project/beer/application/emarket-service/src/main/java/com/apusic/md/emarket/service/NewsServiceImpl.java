package com.apusic.md.emarket.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.News;

@Service("emarket_NewsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private CrudService crudService;

	@Transactional(readOnly = true)
	public Page<News> findNews4Page(GenericQueryObject queryObject) {
		//按照时间降序排列
		queryObject.getDetachedCriteria().addOrder(Order.desc("createTime"));
		return queryService.findPageBy(queryObject
				.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	@Transactional(readOnly = true)
	public Page<News> getAllNews() {
		GenericQueryObject queryObject = new GenericQueryObject(News.class);
		return findNews4Page(queryObject);
	}

	@Transactional
	public void deleteNewsById(int id) {
		crudService.delete(News.class, id);
	}

	@Transactional
	public void createNews(News news) {
		news.setCreateTime(new Date());
		crudService.create(news);
	}

	@Transactional
	public void updateNews(News news) {
		crudService.update(news);
	}

	public List<News> getTopNews() {
		DetachedCriteria criteria = DetachedCriteria.forClass(News.class);
		criteria.addOrder(Order.desc("createTime"));
		return queryService.findBy(criteria,0,configService
				.getIntegerValueByKey("newsSize"));
	}



}
