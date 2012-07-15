package com.apusic.md.emarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.emarket.dao.ProductCategoryDao;
import com.apusic.md.model.emarket.CategoryAttr;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.StateType;

@Service("emarket_productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private CrudService crudService; 

    @Autowired
    private QueryService queryService;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    public List<ProductCategory> findAll() {
        return queryService.findAll(ProductCategory.class);
    }

    @Transactional
    public void disable(int id) {
        updateState(StateType.DISABLED, id);
    }

    @Transactional
    public void enable(int id) {
        updateState(StateType.ENABLED, id);
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> findByExample(ProductCategory example) {
        return productCategoryDao.findByExample(example);
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> findByLevel(int level) {
        return productCategoryDao.findByLevel(level);
    }

    private void updateState(StateType type, int id) {
        if (id <= 0) {
            return;
        }
        ProductCategory category = crudService.retrieve(ProductCategory.class, id);
        if (category != null) {
            category.setState(type);
            crudService.update(category);
        }
    }

    @Transactional(readOnly = true)
    public ProductCategory findById(int id) {
        return crudService.retrieve(ProductCategory.class, id);
    }

    @Transactional
    public void deleteById(int id) {
        crudService.delete(ProductCategory.class, id);
    }

    @Transactional
    public void updateCategory(ProductCategory category) {
        crudService.update(category);
    }

    @Transactional
    public void addCategory(ProductCategory pc1) {
        crudService.create(pc1);
    }
    @Transactional
    public boolean updateState(int id, StateType state) {
        if(id <= 0 || state == null){
            return false;
        }
        ProductCategory category = crudService.retrieve(ProductCategory.class, id);
        if(category == null){
            return false;
        }
        category.setState(state);
        Set<ProductCategory> childrens = category.getChildrens();
        setCategoryState(childrens,state);
        this.updateCategory(category);
        return true;

    }

    private void setCategoryState(Set<ProductCategory> childrens,StateType state){
        if(childrens != null && childrens.size() > 0){
            for(ProductCategory cate : childrens){
                cate.setState(state);
                setCategoryState(cate.getChildrens(),state);
            }
        }
    }

    @Transactional
    public void saveCategoryAttr(int categoryId, Set<CategoryAttr> attrs) {
        ProductCategory category =  this.findById(categoryId);
        Set<CategoryAttr> atrSets = category.getCategoryAttrs();
        crudService.deleteAll(atrSets);
        for(CategoryAttr attrNew : attrs){
            attrNew.setCategory(category);
        }
        atrSets.clear();
        atrSets.addAll(attrs);
        crudService.update(category);
    }

    @Transactional
    public void updateSerialNumber(int[] changeIds, int[] changeNumbers) {
        if(ArrayUtils.isEmpty(changeIds) || ArrayUtils.isEmpty(changeNumbers)){
            return;
        }
        List<ProductCategory> categories = productCategoryDao.findByIds(changeIds);
        for(int i=0;i<changeIds.length;i++){
            for(ProductCategory category : categories){
                if(category.getId() == changeIds[i]){
                    category.setSerialNumber(changeNumbers[i]);
                }
            }
        }
    }

    @Transactional(readOnly=true)
    public boolean isUsedProduct(int categoryId) {
        ProductCategory pc = findById(categoryId);
        List<Integer> ids = new ArrayList<Integer>();
        findAllCategoryIds(pc, ids);
        long count = productCategoryDao.findProductCountByCategoryIds(ArrayUtils.toPrimitive(ids.toArray(new Integer[ids.size()])));
        return count > 0;
    }
    
    /**
     * 递归查找该类别ID以及其下所有子类别ID
     * @param pc
     * @param ids
     * @return
     */
    private List<Integer> findAllCategoryIds(ProductCategory pc, List<Integer> ids){
        if(pc == null ){
            return ids;
        }
        ids.add(pc.getId());
        if(pc.getChildrens() == null){
            return ids;
        }
        for(ProductCategory children : pc.getChildrens()){
            findAllCategoryIds(children, ids);
        }
        return ids;
    }

    @Transactional(readOnly=true)
    public String findCategoryFullName(int id) {
        ProductCategory category = this.findById(id);
        String name = category.getName();
        ProductCategory parent = category.getParent();
        if(parent != null){
            return parent.getName()+" > " + name;
        }
        return name;
    }

    @Transactional(readOnly=true)
	public List<ProductCategory> findChildCategory(int id) {
		ProductCategory findById = this.findById(id);
		List<ProductCategory> result = new ArrayList<ProductCategory>();
		if(findById!=null){
			Set<ProductCategory> childrens = findById.getChildrens();
			for(ProductCategory item : childrens){
				result.add(item);
			}
		}
		return result;
	}
}
