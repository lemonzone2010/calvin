package com.apusic.md.emarket.service;

import java.util.List;
import java.util.Set;

import com.apusic.md.model.emarket.CategoryAttr;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.StateType;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public interface ProductCategoryService {
	@Cacheable(cacheName = "ProductCategoryService")
    List<ProductCategory> findAll();

    List<ProductCategory> findByExample(ProductCategory example);
    @Cacheable(cacheName = "ProductCategoryService")
    List<ProductCategory> findByLevel(int level);
    @Cacheable(cacheName = "ProductCategoryService")
    List<ProductCategory> findChildCategory(int id);

    ProductCategory findById(int id);  

    @TriggersRemove(cacheName="ProductCategoryService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void updateCategory(ProductCategory category);

    @TriggersRemove(cacheName="ProductCategoryService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
     void deleteById(int id);

    @TriggersRemove(cacheName="ProductCategoryService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
     void addCategory(ProductCategory pc1);

    @TriggersRemove(cacheName="ProductCategoryService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    boolean updateState(int id, StateType state);

    @TriggersRemove(cacheName="ProductCategoryService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
    void saveCategoryAttr(int id, Set<CategoryAttr> attrs);

    @TriggersRemove(cacheName="ProductCategoryService",removeAll=true,triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
     void updateSerialNumber(int[] changeIds, int[] changeNumbers);

    boolean isUsedProduct(int categoryId);
    
    @Cacheable(cacheName = "ProductCategoryService")
    String findCategoryFullName(int id);

}
