package com.apusic.ebiz.framework.core.solr;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class HibernateSolrIntegrator implements Integrator{
@Override
	@SuppressWarnings("unchecked")
	public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {
		
		//covert hibernate mappings to solr mapping
		Iterator<PersistentClass> classMappings = configuration.getClassMappings();
		while(classMappings.hasNext()) {
			PersistentClass persistentClass = classMappings.next();
			try {
				Class<?> clazz = Class.forName(persistentClass.getClassName());
				if(clazz.isAnnotationPresent(Indexed.class)) {
					continue;
				}
				
				SolrEntity entity=new SolrEntity();
				entity.setTableName(persistentClass.getTable().getName());
				
				
				SolrMapping.addField(clazz, entity);
				entity.setAnnotations(clazz.getDeclaredAnnotations());
				
				
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field delcaredField : declaredFields) {
					SolrField field=new SolrField();
					entity.addField(delcaredField.getName(), field);
					field.setAnnotations(delcaredField.getDeclaredAnnotations());
				}
				
				Property idProperty = persistentClass.getIdentifierProperty();
				loadProperty(entity, idProperty);
				
				for (Iterator<Property> propertyIterator = persistentClass.getPropertyIterator(); propertyIterator.hasNext();) {
					Property property = (Property) propertyIterator.next();
					
					loadProperty(entity, property);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		//System.out.println(SolrMapping.getMappingMap());
		//TODO 解析实体类，将@Field定义的实体及相关的属性，提交到solr/addSchema?
	}

	private void loadProperty(SolrEntity entity, Property property) {
		SolrField field = entity.get(property.getName());
		if(null==field) {
			field=new SolrField();
			entity.addField(property.getName(), field);
		}
		
		Value column= property.getValue();
		if(column.getColumnIterator().hasNext()) {
			Column next3 = (Column) column.getColumnIterator().next();
			field.setColumnName(next3.getName());
		}
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
