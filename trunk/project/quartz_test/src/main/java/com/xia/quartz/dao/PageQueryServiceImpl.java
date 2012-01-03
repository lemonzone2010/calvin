package com.xia.quartz.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component(value = "pageQueryService")
public class PageQueryServiceImpl implements PageQueryService {//TODO:Test case
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public PageQueryServiceImpl(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public <T> T findBy(Class<T> clazz, String property, String value) {
		List<T> find = hibernateTemplate.find("from " + clazz.getName() + " where " + property + "=?", value);
		if (find.size() > 0) {
			return find.get(0);
		}
		return null;
	}


	public long getRowCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		long rowCnt = (Long) hibernateTemplate.findByCriteria(criteria,0,1).get(0);
		return rowCnt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		return hibernateTemplate.findByCriteria(DetachedCriteria.forClass(clazz));
	}

	@Override
	public <T> long getRowCount(Class<T> clazz) {
		String hql = String.format("SELECT COUNT(*) FROM %s a ", clazz.getSimpleName());
		long rowCnt = ((Long) hibernateTemplate.find(hql).get(0)).intValue();
		return rowCnt;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

	public <T> long getRowCountByExample(T t) {
		DetachedCriteria criteria = DetachedCriteria.forClass(t.getClass());
		criteria.setProjection(Projections.rowCount()).add(Example.create(t));
		long rowCnt = (Long) hibernateTemplate.findByCriteria(criteria).get(0);
		return rowCnt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findListBy(Class<T> clazz, String property, String value) {
		return hibernateTemplate.find("from " + clazz.getName() + " where " + property + "=?", value);
	}
}
