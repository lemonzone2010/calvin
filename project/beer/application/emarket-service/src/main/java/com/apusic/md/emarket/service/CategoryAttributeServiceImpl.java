package com.apusic.md.emarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.emarket.dao.CategoryAttributeDao;
import com.apusic.md.model.emarket.CategoryAttr;

@Service("emarket_CategoryAttributeService")
public class CategoryAttributeServiceImpl implements CategoryAttributeService {

	@Autowired
	private CrudService crudService;
	
	@Autowired
	private QueryService queryService;

	@Autowired
	private CategoryAttributeDao categoryAttributeDao;

	@Transactional(readOnly=true)
	public List<CategoryAttr> getAttibutesByCategoryId(Integer categoryId) {
		return categoryAttributeDao.getAttibutesByCategoryId(categoryId);
	}

	@Transactional
    public void createAttr(CategoryAttr attr) {
        crudService.create(attr);
    }

	@Transactional(readOnly=true)
    public CategoryAttr findById(int id){
       return crudService.retrieve(CategoryAttr.class, id);
    }

    @Transactional
    public void deleteById(int id) {
        crudService.delete(CategoryAttr.class, id);
    }
    
    @Transactional(readOnly=true)
	public List<CategoryAttr> findAll() {
		return queryService.findAll(CategoryAttr.class);
	}
}
