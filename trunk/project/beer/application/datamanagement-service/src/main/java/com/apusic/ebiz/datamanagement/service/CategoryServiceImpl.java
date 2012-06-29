package com.apusic.ebiz.datamanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.datamanagement.DatamanagementException;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.datamanagement.Category;

@Service("datamanagement_CategoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CrudService crudService;

	@Autowired
	private QueryService queryService;

	@Transactional
	public void addCategory(Category category) {
		this.crudService.create(category);
	}

	@Transactional
	public void deleteCategory(int id) {
		Category category = this.crudService.retrieve(Category.class, id);
		if(category.getDatas()!=null && category.getDatas().size()>0){
			throw new DatamanagementException("DataManagement-Err-001");
		}
		this.crudService.delete(category);
	}

	@Transactional(readOnly=true)
	public List<Category> getAllCategorys() {
		return this.queryService.findAll(Category.class,"id","asc");
	}

	@Transactional(readOnly=true)
	public Category getCategory(int id) {
		return this.crudService.retrieve(Category.class, id);
	}
	
	@Transactional(readOnly=true)
	public Category getCategory(Category category) {
		return this.queryService.findByExample(category).get(0);
	}

	@Transactional
	public void updateCategory(Category category) {
		this.crudService.update(category);
	}

	public Category getCategoryByName(String name) {
		Category category = new Category();
		category.setName(name);
		List<Category> categories = queryService.findByExample(category);
		if (categories.size()>0) {
			return categories.get(0);
		}
		return null;
	}
}
