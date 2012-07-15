package com.apusic.md.emarket.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductCategory;


@Repository("emarket_ProductCategoryDao")
public class ProductCategoryDaoImpl implements ProductCategoryDao {

    @Autowired
    private QueryService queryService; 
    
    public List<ProductCategory> findByLevel(int level){
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductCategory.class);
        criteria.add(Restrictions.eq("level", level));
        criteria.addOrder(Order.asc("serialNumber"));
        criteria.addOrder(Order.asc("id"));
        return queryService.findBy(criteria);
    }

    public List<ProductCategory> findByIds(int[] changeIds) {
        if(ArrayUtils.isEmpty(changeIds)){
            return new ArrayList<ProductCategory>(0);
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductCategory.class);
        criteria.add(Restrictions.in("id", ArrayUtils.toObject(changeIds)));
        return queryService.findBy(criteria);
    }

    public List<ProductCategory> findByExample(ProductCategory category) {
        if(category == null){
            return new ArrayList<ProductCategory>(0);
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(ProductCategory.class);
        if(category.getId() > 0){
            criteria.add(Restrictions.eq("id", category.getId()));
        }
        if(category.getLevel() > 0){
            criteria.add(Restrictions.eq("level", category.getLevel()));
        }
        if(StringUtils.isNotEmpty(category.getName())){
            criteria.add(Restrictions.eq("name", category.getName()));
        }
        if(category.getState() != null){
            criteria.add(Restrictions.eq("state", category.getState()));
        }
        criteria.addOrder(Order.asc("serialNumber"));
        criteria.addOrder(Order.asc("id"));
        return queryService.findBy(criteria);
    }

    public long findProductCountByCategoryId(int categoryId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
        criteria.add(Restrictions.eq("category.id", categoryId));
        return queryService.getRowCount(criteria);
    }
    
    public long findProductCountByCategoryIds(int[] ids) {
        if(ArrayUtils.isEmpty(ids)){
            return 0;
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
        criteria.add(Restrictions.in("category.id", ArrayUtils.toObject(ids)));
        return queryService.getRowCount(criteria);
    }
}
