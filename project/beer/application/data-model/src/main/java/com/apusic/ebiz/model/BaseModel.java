package com.apusic.ebiz.model;

import java.io.Serializable;

import com.apusic.ebiz.framework.core.model.IdEntity;

/**
 * @author guoqing.gu
 *
 */
public abstract class BaseModel extends IdEntity implements Serializable{

    private static final long serialVersionUID = 2016887044443662579L;


   // public abstract int getId();

  //  public abstract void setId(int id);

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    public <T> T newInstance(Class<T> clazz){
    	try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Impossible stuff happens");
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Impossible stuff happens");
		}
    }
}
