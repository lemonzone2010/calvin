package com.apusic.ebiz.datamanagement.service;

import java.util.List;

import com.apusic.ebiz.model.datamanagement.Category;

public interface CategoryService {
	void addCategory(Category category);

	void deleteCategory(int id);

	void updateCategory(Category category);

	List<Category> getAllCategorys();

	Category getCategory(int id);
	
	Category getCategory(Category category);

	Category getCategoryByName(String name);
}
