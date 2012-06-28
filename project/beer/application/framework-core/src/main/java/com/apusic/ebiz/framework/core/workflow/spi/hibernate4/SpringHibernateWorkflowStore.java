/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.apusic.ebiz.framework.core.workflow.spi.hibernate4;

import com.apusic.ebiz.framework.core.dao.HibernateCallback;
import com.apusic.ebiz.framework.core.dao.HibernateTemplate;
import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.spi.hibernate3.AbstractHibernateWorkflowStore;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

import java.util.Map;


/**
 * @author masini
 *
 * New Refactored Spring Managed Hibernate Store.
 * Look at @link NewSpringHibernateFunctionalWorkflowTestCase for a use case.
 */
public class SpringHibernateWorkflowStore extends AbstractHibernateWorkflowStore {
    //~ Instance fields ////////////////////////////////////////////////////////

    private SessionFactory sessionFactory;

    //~ Constructors ///////////////////////////////////////////////////////////

    public SpringHibernateWorkflowStore() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void init(Map props) throws StoreException {
    }

    protected Object execute(final InternalCallback action) throws StoreException {
        HibernateTemplate template = new HibernateTemplate(getSessionFactory());

        return template.execute(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    try {
                        return action.doInHibernate(session);
                    } catch (StoreException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
    }
}
