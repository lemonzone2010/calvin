package com.apusic.ebiz.framework.core.solr;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.MultiValueMap;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SimpleValue;
import org.hibernate.mapping.Value;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class HibernateSolrIntegrator implements Integrator{
class DocField{
	String name;
	String columnName;
	Annotation[] annos;
}
static class Doc{
	static Map<String,DocField> field=new HashMap<String,DocField>();
	List<DocField> fields;
}
	@Override
	public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		System.out.println(configuration);
		
		Iterator<PersistentClass> classMappings = configuration.getClassMappings();
		while(classMappings.hasNext()) {
			PersistentClass next = classMappings.next();
			try {
				Class<?> clazz = Class.forName(next.getClassName());
				Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field field : declaredFields) {
					Annotation[] declaredAnnotations2 = field.getDeclaredAnnotations();
					DocField doc=new DocField();
					doc.annos=declaredAnnotations2;
					Doc.field.put(field.getName(), doc);
				}
				for (Iterator propertyIterator = next.getPropertyIterator(); propertyIterator.hasNext();) {
					Property next2 = (Property) propertyIterator.next();
					Value value= next2.getValue();
					DocField object = Doc.field.get(next2.getName());
					if(null==object) {
						object=new DocField();
						Doc.field.put(next2.getName(), object);
					}
					if(value.getColumnIterator().hasNext()) {
					Column next3 = (Column) value.getColumnIterator().next();
					object.columnName=next3.getName();
					}
					//object.columnName=value.getColumnIterator().next().toString();
					
					System.out.println(value);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(next);
		}
		//TODO 解析实体类，将@Field定义的实体及相关的属性，提交到solr/addSchema?
	}

	@Override
	public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
		
	}

}
