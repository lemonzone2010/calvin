package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.Brand;

public interface BrandService {

	List<Brand> findAll();

	List<Brand> findEnabled();

	List<Brand> findByCategoryId(int categoryId);

	void addBrand(Brand brand);

    Brand findById(int id);

	List<Brand> findBrandsByLevel2CategoryId(int categoryId);
	List<Brand> findBrandsByLevel2CategoryId(int categoryId,Integer top);

	/**
	 * 分页查询品牌
	 *
	 * @param queryObject
	 * @return
	 */
	Page<Brand> findBrands4Page(GenericQueryObject queryObject);

	void updateBrandCategeries(Brand brand, List<Integer> categeries);

	List<Brand> getHomepageBrand(int start,int size);

	Brand getOtherBrand();

	boolean deleteBrand(Brand brand);

	List<Brand> findByLevel2CategoryId(int id);
}
