package com.apusic.ebiz.framework.core.fulltextsearch;

import java.io.Serializable;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.search.FullTextSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.HibernateTemplate;

@Repository("ebiz_IndexService")
public class IndexServiceImpl implements IndexService {

	private static final int MAX_RESULT = 10000;
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public IndexServiceImpl(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public <T> void index(Class<T>... classes) {
		hibernateTemplate.flush();
		Session session = hibernateTemplate.getSession();
		FullTextSession ftSession = org.hibernate.search.Search
				.getFullTextSession(session);
		for (Class t : classes) {
			Criteria criteria=ftSession.createCriteria(t);
			criteria.setMaxResults(MAX_RESULT);
			int start = 1;
			int resultNum =MAX_RESULT;
			while (resultNum >= MAX_RESULT) {
				criteria.setFirstResult(start);
				List<T> results = criteria.list();
				for (T result : results) {
					ftSession.index(result);
				
				}
				resultNum = results.size();
				start+=MAX_RESULT;
				ftSession.flushToIndexes();
				ftSession.clear();
			}
		}
	}
	
	public <T> void indexByCriteria(Class<T> clazz,Criterion... criterions) {
		hibernateTemplate.flush();
		Session session = hibernateTemplate.getSession();
		FullTextSession ftSession = org.hibernate.search.Search
				.getFullTextSession(session);
		Criteria criteria = ftSession.createCriteria(clazz);
			for (Criterion cr : criterions) {
				criteria.add(cr);
			}
			//criteria.add(Restrictions.between("id", 300000, 400000));
			criteria.setMaxResults(MAX_RESULT);
			int start = 1;
			int resultNum =MAX_RESULT;
			ftSession.setFlushMode(FlushMode.MANUAL);   
			ftSession.setCacheMode(CacheMode.IGNORE);  
			while (resultNum >= MAX_RESULT) {
				
				criteria.setFirstResult(start);
				List<T> results = criteria.list();
				for (T result : results) {
					ftSession.index(result);
				}
				
				resultNum = results.size();
				start+=MAX_RESULT;
				ftSession.flushToIndexes();
				ftSession.clear();
		
			}
		
	}
	public <T, I extends Serializable> void purge(Class<T> clazz, I id) {
		hibernateTemplate.flush();
		Session session = hibernateTemplate.getSession();
		FullTextSession ftSession = org.hibernate.search.Search
				.getFullTextSession(session);
		ftSession.purge(clazz, id);
		ftSession.flushToIndexes();
	}

	public <T> void purgeAll(Class<T> clazz) {
		hibernateTemplate.flush();
		Session session = hibernateTemplate.getSession();
		FullTextSession ftSession = org.hibernate.search.Search
				.getFullTextSession(session);
		ftSession.purgeAll(clazz);
	}

	public <T, I extends Serializable> void index(Class<T> entity, I id) {
		hibernateTemplate.flush();
		Session session = hibernateTemplate.getSession();
		FullTextSession ftSession = org.hibernate.search.Search
				.getFullTextSession(session);
		T attachedEntity = (T) ftSession.get(entity, id);
		ftSession.index(attachedEntity);
	}

	public <T, I extends Serializable> void delete(Class<T> entity, I id) {
		hibernateTemplate.flush();
		Session session = hibernateTemplate.getSession();
		FullTextSession ftSession = org.hibernate.search.Search
				.getFullTextSession(session);
		T attachedEntity = (T) ftSession.get(entity, id);
		ftSession.delete(attachedEntity);

	}
}
