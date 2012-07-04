package com.apusic.ebiz.framework.core.solr;

import java.util.concurrent.Callable;

import org.hibernate.SessionFactory;
import org.hibernate.search.util.logging.impl.Log;
import org.hibernate.search.util.logging.impl.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.apusic.ebiz.framework.core.util.TransactionHelper;
import com.xia.search.solr.schema.DocumentHelper;
import com.xia.search.solr.schema.event.HibernateSolrIntegrator;

/**
 * Integrates Hibernate Search into Hibernate Core by registering its needed listeners
 *
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 * @author Steve Ebersole
 */
//@Component
public class SolrEventInitalize implements InitializingBean{

	private static final Log log = LoggerFactory.make();
	public static final String AUTO_REGISTER = "hibernate.search.autoregister_listeners";
	
	//@Autowired
	private SessionFactory sessionFactory;
	private TransactionTemplate transactionTemplate;

	//@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			TransactionHelper.doInTransaction(transactionTemplate, new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					//DocumentHelper.updateSolrSchema(HibernateSolrIntegrator.getConfiguration(), sessionFactory.getCurrentSession());
					return null;
				}
			});
		} catch (Exception e) {
			log.error("更新Solr Schema出错", e);
		}
	}



	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



}
