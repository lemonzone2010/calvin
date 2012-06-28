package com.apusic.ebiz.framework.core.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate4.HibernateQueryException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

public class HibernateTemplate {
	private SessionFactory sessionFactory;

	public HibernateTemplate(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
		//return sessionFactory.openSession();
		//TODO 改成mysql试试。
		/*try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			e.printStackTrace();//FIXME session get problem
			return sessionFactory.openSession();
		}*/
	}

	List findByCriteria(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getSession()).list();
	}

	List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
		return criteria.getExecutableCriteria(getSession()).setFirstResult(firstResult).setMaxResults(maxResults)
				.list();
	}

	public List find(String queryString, Object... values) {
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject.list();
	}
	public List find(String queryString, int firstResult, int maxResults) {
		Query query = getSession().createQuery(queryString);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}

	public List findByExample(final String entityName, final Object exampleEntity, final int firstResult,
			final int maxResults) {
		Criteria executableCriteria = (entityName != null ? getSession().createCriteria(entityName) : getSession()
				.createCriteria(exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		if (firstResult >= 0) {
			executableCriteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			executableCriteria.setMaxResults(maxResults);
		}
		return executableCriteria.list();
	}
	
	public List findByExample(Object exampleEntity) throws DataAccessException {
		return findByExample(null, exampleEntity, -1, -1);
	}

	public List findByExample(String entityName, Object exampleEntity) throws DataAccessException {
		return findByExample(entityName, exampleEntity, -1, -1);
	}

	public List findByExample(Object exampleEntity, int firstResult, int maxResults) throws DataAccessException {
		return findByExample(null, exampleEntity, firstResult, maxResults);
	}
	public <T> T execute(HibernateCallback<T> action) throws DataAccessException {
		T result;
		try {
			result = action.doInHibernate(getSession());
		} catch (Exception e) {
			throw new DataAccessResourceFailureException("" + e.getMessage(), e);
		}
		return (T) result;
	}
	public List executeFind(HibernateCallback<?> action) throws DataAccessException {
		return (List) execute(action);
	}

	public void flush() {
		getSession().flush();
		
	}

	

}
