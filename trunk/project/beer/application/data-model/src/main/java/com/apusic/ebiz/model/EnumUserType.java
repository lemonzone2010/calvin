package com.apusic.ebiz.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.EnumSet;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class EnumUserType<E extends Enum<E>> implements UserType {
	private static Logger log = Logger.getLogger(EnumUserType.class);
	
	private Class<E> clazz = null;

	private static final int[] SQL_TYPES = { Types.VARCHAR };

	public EnumUserType(Class<E> c) {
		this.clazz = c;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y)
			return true;
		if (null == x || null == y)
			return true;
		return x.equals(y);
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
			throws HibernateException, SQLException {
		E result = null;

		if (resultSet.getString(names[0]) == null){
			return null;
		}

		String name = resultSet.getString(names[0]).toUpperCase();
		if(name!=null){
			result = Enum.valueOf(clazz, name);
		}
		return result;
	}

	public void nullSafeSet(PreparedStatement prepareStatement, Object value,
			int index) throws HibernateException, SQLException {
		if (null == value) {
			prepareStatement.setNull(index, Types.VARCHAR);
		} else {
			prepareStatement.setString(index, ((Enum) value).name());
		}
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	public Class returnedClass() {
		return clazz;
	}

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] strings,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		  final int val = resultSet.getInt(strings[0]);
	        Object obj = null;
	        if (!resultSet.wasNull()) {
	            EnumSet enumSet = EnumSet.allOf(clazz);
	            for (Object object : enumSet) {
	                if (object instanceof Enum) {
	                    Enum e = (Enum) object;
	                    if (e.ordinal() == val) {
	                        obj = e;
	                    }
	                }
	            }
	        }
	        return obj;
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
			SessionImplementor arg3) throws HibernateException, SQLException {
		  if (null == value) {
	            preparedStatement.setNull(index, Types.INTEGER);
	        } else {
	            preparedStatement.setInt(index, ((Enum) value).ordinal());
	        }
		
	}
}
