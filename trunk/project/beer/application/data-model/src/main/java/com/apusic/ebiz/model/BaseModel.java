package com.apusic.ebiz.model;

import java.io.Serializable;

/**
 * @author guoqing.gu
 *
 */
public abstract class BaseModel implements Serializable{

    private static final long serialVersionUID = 2016887044443662579L;

    @Override
    public int hashCode() {
        final int prime = 31;
        return (this.getClass().hashCode() * getId()) * prime;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseModel other = (BaseModel) obj;
        if (getId() == 0) {
            return false;
        } else if (getId() != other.getId()) {
            return false;
        }
        return true;
    }

    public abstract int getId();

    public abstract void setId(int id);

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
