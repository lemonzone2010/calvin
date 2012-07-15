package com.apusic.md.emarket.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.CategoryAttr;

@Repository("emarket_CategoryAttributeDao")
public class CategoryAttributeDaoImpl implements CategoryAttributeDao {

	@Autowired
	private QueryService queryService;

	public List<CategoryAttr> getAttibutesByCategoryId(Integer categoryId) {
		DetachedCriteria criterial = DetachedCriteria.forClass(CategoryAttr.class);
		criterial.add(Restrictions.eq("category.id", categoryId));
		return queryService.findBy(criterial);
	}

}
