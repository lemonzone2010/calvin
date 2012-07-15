package com.apusic.md.emarket.dao;

import java.util.List;

import com.apusic.md.model.emarket.CategoryAttr;

public interface CategoryAttributeDao {

	List<CategoryAttr> getAttibutesByCategoryId(Integer categoryId);

}
