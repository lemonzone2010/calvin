package com.apusic.md.emarket.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.Brand;
import com.apusic.md.model.emarket.ProductCategory;
import com.apusic.md.model.emarket.StateType;

@Service("emarket_BrandService")
public class BrandServiceImpl implements BrandService {

	@Autowired
	private QueryService queryService;

	@Autowired
	private CrudService crudService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Autowired
	private ProductService productService;

	@Transactional(readOnly=true)
	public List<Brand> findAll() {
		return queryService.findAll(Brand.class);
	}
	@Transactional(readOnly=true)
	public List<Brand> findEnabled() {
		Brand b=new Brand();
		b.setState(StateType.ENABLED);
		return queryService.findByExample(b);
	}
	@Transactional(readOnly=true)
	public List<Brand> findByCategoryId(int categoryId) {
		DetachedCriteria c=DetachedCriteria.forClass(Brand.class);
		c.createAlias("categorys", "categorys");
		c.add(Restrictions.eq("categorys.id",categoryId));
		return queryService.findBy(c);
	}
	@Transactional
	public void addBrand(Brand brand) {
		brand.setState(StateType.ENABLED);
		crudService.create(brand);
	}

    public Brand findById(int id) {
        return crudService.retrieve(Brand.class, id);
    }
    
    @Transactional(readOnly=true)
	public List<Brand> findBrandsByLevel2CategoryId(int categoryId) {
    	return findBrandsByLevel2CategoryId(categoryId, null);
    }
    @Transactional(readOnly=true)
	public List<Brand> findBrandsByLevel2CategoryId(int categoryId,Integer top) {
		DetachedCriteria subCategorys = DetachedCriteria.forClass(ProductCategory.class, "category")
			.setProjection(Property.forName("category.id"));
		subCategorys.createAlias("parent", "parent");
		subCategorys.add(Restrictions.eq("parent.id",categoryId));

		DetachedCriteria brands=DetachedCriteria.forClass(Brand.class,"brand");
		brands.createAlias("categorys", "categorys");
		brands.add( Property.forName("categorys.id").in(subCategorys));
		brands.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (top!=null) {
			return queryService.findBy(brands, 0, top);
		}
		return queryService.findBy(brands);
	}
    @Transactional(readOnly=true)
	public Page<Brand> findBrands4Page(GenericQueryObject queryObject) {
		queryObject.getDetachedCriteria().addOrder(Order.desc("id"));
		return queryService.findPageBy(queryObject
				.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	@Transactional
	public void updateBrandCategeries(Brand brand, List<Integer> categeries) {
		//得到Brand旧的集合
		List<Integer> originalCategoryIds = new ArrayList<Integer>();
		for(ProductCategory category:brand.getCategorys()){
			originalCategoryIds.add(category.getId());
		}
		// 得到 取消的关联 集合
		List<Integer> cancelledCategeries = new ArrayList<Integer>();
		for(int id:originalCategoryIds){
			if(!categeries.contains(id)){
				cancelledCategeries.add(id);
			}
		}
		// 得到 增加的关联集合
		List<Integer> addedCategeries = new ArrayList<Integer>();
		for(int addId:categeries){
			if(!originalCategoryIds.contains(addId)){
				addedCategeries.add(addId);
			}
		}
		//删除取消的关联信息
		abolishBrandCategeries(brand.getId(),cancelledCategeries);
		// 增加关联
		addBrandCategeries(brand.getId(),addedCategeries);

	}

	/**
	 * 添加关联关系
	 * @param brandId
	 * @param addedCategeries
	 */
	@Transactional
	private void addBrandCategeries(int brandId, List<Integer> addedCategeries) {
		Brand brand = crudService.retrieve(Brand.class, brandId);
		if (brand == null) {
			return;
		}
		for(Integer categeryId:addedCategeries){
			ProductCategory category =crudService.retrieve(ProductCategory.class, categeryId);
			brand.getCategorys().add(category);
		}
	}

	/**
	 * 删除关联关系
	 * @param brandId
	 * @param cancelledCategeries
	 */
	@Transactional
	private void abolishBrandCategeries(Integer brandId,
			List<Integer> cancelledCategeries) {
		Brand brand = crudService.retrieve(Brand.class, brandId);
		if (brand == null) {
			return;
		}
		for(Integer categeryId:cancelledCategeries){
			ProductCategory category =crudService.retrieve(ProductCategory.class, categeryId);
			brand.getCategorys().remove(category);
		}
	}

	@Transactional(readOnly=true)
	public List<Brand> getHomepageBrand(int start, int size) {
		return queryService.findRange(Brand.class, start, size);
	}

	public Brand getOtherBrand() {
		String name = configService.getValueByKey("otherBrandName");
		Brand brand = new Brand();
		brand.setName(name);
		List<Brand> brands = queryService.findByExample(brand);
		if (brands!=null && brands.size()>0) {
			return brands.get(0);
		}
		return null;
	}

	@Transactional
	public boolean deleteBrand(Brand brand) {
		if(brand == null){
			return false;
		}
		brand.getCategorys().clear();	//清空集合
		boolean hasProducts =  productService.hasProductsByBrand(brand);
		if(!hasProducts){
			crudService.delete(brand);
			return true;
		}
		return false;
	}
	public List<Brand> findByLevel2CategoryId(int id) {
		DetachedCriteria c=DetachedCriteria.forClass(Brand.class);
		c.createAlias("categorys", "categorys");
		c.add(Restrictions.eq("categorys.parent.id",id));
		return queryService.findBy(c);
	}
}
