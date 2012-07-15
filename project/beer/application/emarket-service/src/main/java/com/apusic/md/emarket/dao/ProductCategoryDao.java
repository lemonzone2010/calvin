package com.apusic.md.emarket.dao;

import java.util.List;

import com.apusic.md.model.emarket.ProductCategory;

public interface ProductCategoryDao {

    List<ProductCategory> findByLevel(int level);
    
    List<ProductCategory> findByIds(int[] changeIds);
    
    List<ProductCategory> findByExample(ProductCategory category);
    
    long findProductCountByCategoryId(int categoryId); 
    
    long findProductCountByCategoryIds(int[] ids);
}
