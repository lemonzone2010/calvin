package com.apusic.md.emarket.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.ProductImage;


@Repository("emarekt_ProductImageDao")
public class ProductImageDaoImpl implements ProductImageDao {

	@Autowired
	private QueryService queryService;

	public ProductImage getOneProductImageByProductId(Integer id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductImage.class);
		criteria.add(Restrictions.eq("product.id", id));
		criteria.addOrder(Order.asc("serialNumber"));
		List<ProductImage> images = queryService.findBy(criteria, 0, 1);
		if (images.size()>0) {
			return images.get(0);
		}
		return null;
	}

	public List<ProductImage> getProductImagesByProductId(Integer id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductImage.class);
		criteria.add(Restrictions.eq("product.id", id));
		criteria.addOrder(Order.asc("serialNumber"));
		return queryService.findBy(criteria);
	}

}
