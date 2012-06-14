package com.apusic.ebiz.framework.core.mock;

import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory bean for producing mock objects
 * @author achen
 *
 * @param <T>
 */
public class MockFactoryBean <T> implements FactoryBean<T>{

	private Class<T> type;

    public void setType(final Class<T> type) {
        this.type = type;
    }

    public T getObject() throws Exception {
        return (T) Mockito.mock(type);
    }

    public Class<T> getObjectType() {
        return type;
    }

    public boolean isSingleton() {
        return true;
    }

}
