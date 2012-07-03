package com.apusic.ebiz.framework.core.solr;

import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.IntegratorService;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.search.util.logging.impl.Log;
import org.hibernate.search.util.logging.impl.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.apusic.ebiz.framework.core.util.TransactionHelper;
import com.xia.search.solr.schema.DocumentHelper;

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
	private static Configuration configuration;

	
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
					DocumentHelper.updateSolrSchema(configuration, sessionFactory.getCurrentSession());
					return null;
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void setConfiguration(Configuration configuration) {
		SolrEventInitalize.configuration = configuration;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



}
