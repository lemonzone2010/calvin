package com.apusic.ebiz.framework.core.dao;

import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

	@SuppressWarnings("unchecked")
	public <T> Page<T> findPage(Class<T> clazz, Page<T> p) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

		for (Entry<String, String> t : p.getQMap().entrySet()) {
			criteria.add(Restrictions.ilike(t.getKey(), t.getValue() + "%"));
		}

		if (p.isOrderBySetted()) {
			if (StringUtils.equalsIgnoreCase("asc", p.getOrderDirect()))
				criteria.addOrder(Order.asc(p.getOrderBy()));
			else
				criteria.addOrder(Order.desc(p.getOrderBy()));
		}
		p.setRows((List<T>) hibernateTemplate.findByCriteria(criteria, p.getOffset(), p.getPageSize()));
		p.setTotal(getRowCount(criteria));
		return p;
	}


	public long getRowCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		List<?> list = hibernateTemplate.findByCriteria(criteria);
		return list.size() > 0 ? (Long) list.get(0) : 0;
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
	public <T> Page<T> findPageByExample(final T e, Page<T> p) {
		p.setRows(hibernateTemplate.findByExample(e,p.getOffset(), p.getPageSize()));
		p.setTotal(getRowCountByExample(e));
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findListBy(Class<T> clazz, String property, String value) {
		return hibernateTemplate.find("from " + clazz.getName() + " where " + property + "=?", value);
	}
}
