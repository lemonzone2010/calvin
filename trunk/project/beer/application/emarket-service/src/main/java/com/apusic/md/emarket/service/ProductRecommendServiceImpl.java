package com.apusic.md.emarket.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.emarket.EmarketException;
import com.apusic.md.model.emarket.Product;
import com.apusic.md.model.emarket.ProductRecommend;
import com.apusic.md.model.emarket.ProductStateType;
import com.apusic.md.model.emarket.RecommendType;

@Service("emarket_ProductRecommendService")
public class ProductRecommendServiceImpl implements ProductRecommendService {
	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private CrudService crudService;

	@Autowired
	private ProductService productService;

	@Transactional
	public void addProductRecommend(Product p,
			List<RecommendType> recommendTypes) {
		// 未选中的列表
		List<RecommendType> unSelectTypes = new ArrayList<RecommendType>();
		for (RecommendType type : RecommendType.values()) {
			if (!recommendTypes.contains(type)) {
				// 如果不包含，放在未选择的列表中
				unSelectTypes.add(type);
			}
		}
		// 添加新数据
		for (RecommendType recommendType : recommendTypes) {
			addSingleProductRecommend(p, recommendType);
		}
		// 更新数据
		for (RecommendType unSelectrecommendType : unSelectTypes) {
			abolishProductRecommend(p.getId(), unSelectrecommendType);
		}
	}

	@Transactional(readOnly = true)
	public List<RecommendType> getRecommendTypesByProductId(int productId) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(ProductRecommend.class);
		criteria.add(Restrictions.eq("product.id", productId));
		criteria.setProjection(Projections.property("type"));
		List<RecommendType> recommendTypes = queryService
				.findBy(criteria);
		return recommendTypes;
	}

	@Transactional
	public void abolishProductRecommendById(int productRecommendId) {
		crudService.delete(ProductRecommend.class, productRecommendId);
	}

	@Transactional(readOnly = true)
	public Page<ProductRecommend> findHotProducts4Page(
			GenericQueryObject queryObject) {
		return findProductRecommends4PageByType(queryObject, RecommendType.HOT);
	}

	@Transactional(readOnly = true)
	public Page<ProductRecommend> findNewProductsShelves4Page(
			GenericQueryObject queryObject) {
		return findProductRecommends4PageByType(queryObject, RecommendType.NEW);
	}

	@Transactional(readOnly = true)
	public Page<ProductRecommend> findRecommends4Page(
			GenericQueryObject queryObject) {
		return findProductRecommends4PageByType(queryObject,
				RecommendType.RECOMMEND);
	}

	@Transactional(readOnly = true)
	public List<ProductRecommend> getTopHotProducts(int count) {
		return getTopProductRecommends(RecommendType.HOT, count);
	}

	@Transactional(readOnly = true)
	public List<ProductRecommend> getTopNewProducts(int count) {
		return getTopProductRecommends(RecommendType.NEW, count);
	}
	@Transactional(readOnly = true)
	public List<ProductRecommend> getTopRecommendProducts(int count) {
		return getTopProductRecommends(RecommendType.RECOMMEND, count);
	}



	@Transactional
	public void deleteProductRecommendsByProductId(int productId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductRecommend.class);
		criteria.add(Restrictions.eq("product.id", productId));
		List<ProductRecommend> productRecommends = queryService.findBy(criteria);
		for(ProductRecommend productRecommend:productRecommends){
			crudService.delete(productRecommend);
		}
	}

	@Transactional
	private void addSingleProductRecommend(Product p,
			RecommendType recommendType) {
		// 判断是否已经存在
		if (isRecommend(p.getId(), recommendType)) {
			return;
		}
		ProductRecommend productRecommend = new ProductRecommend();
		productRecommend.setProduct(p);
		productRecommend.setType(recommendType);
		Date date = new Date();
		productRecommend.setStartDate(date);
		productRecommend.setEndDate(DateUtils.addDays(date, 30));
		crudService.create(productRecommend);
	}

	@Transactional(readOnly = true)
	private ProductRecommend getProductRecommendByProductIdAndType(
			int productId, RecommendType recommendType) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(ProductRecommend.class);
		criteria.add(Restrictions.eq("product.id", productId));
		criteria.add(Restrictions.eq("type", recommendType));
		List<ProductRecommend> productRecommends = queryService
				.findBy(criteria);
		if (productRecommends.size() > 0) {
			return productRecommends.get(0);
		}
		return null;
	}

	@Transactional(readOnly = true)
	private boolean isRecommend(int productId, RecommendType recommendType) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(ProductRecommend.class);
		criteria.add(Restrictions.eq("product.id", productId));
		criteria.add(Restrictions.eq("type", recommendType));
		long rowCount = queryService.getRowCount(criteria);
		return rowCount > 0;
	}

	@Transactional
	private void abolishProductRecommend(int productId,
			RecommendType recommendType) {
		// 如果不存在，直接退出
		ProductRecommend productRecommend = getProductRecommendByProductIdAndType(
				productId, recommendType);
		if (productRecommend == null) {
			return;
		}
		crudService.delete(productRecommend);
	}

	@Transactional(readOnly = true)
	private Page<ProductRecommend> findProductRecommends4PageByType(
			GenericQueryObject queryObject, RecommendType recommendType) {
		queryObject.eq("product.state", ProductStateType.PUBLISH);
		queryObject.getDetachedCriteria().addOrder(Order.desc("product.publishedTime"));
		queryObject.eq("type", recommendType);
		return queryService.findPageBy(queryObject
				.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	/**
	 *查看DetachedCriteria中是否存在  别名，防止参数重复别名
	 * @param detachedCriteria
	 * @param path
	 * @return
	 */
	private static String getAlias(DetachedCriteria detachedCriteria, String path) {
		Field originalImplField = null;
		CriteriaImpl impl = null;
		try {
			originalImplField = detachedCriteria.getClass().getDeclaredField("impl");
			originalImplField.setAccessible(true);
			impl = (CriteriaImpl) originalImplField.get(detachedCriteria);
		} catch (Exception e) {
		 throw new EmarketException("getAliasExceptino");
		}
		   Iterator iterator = impl.iterateSubcriteria();
		   while(iterator.hasNext()) {
		    Subcriteria subcriteria = (Subcriteria) iterator.next();
		    if (subcriteria.getPath().equals(path)) {
		     return subcriteria.getAlias();
		    }
		   }
		   return null;
		}


	/**
	 * 根据类型 和 个数 得到 推荐，热卖等 列表
	 * @param recommendType
	 * @param count
	 * @return
	 */
	private List<ProductRecommend> getTopProductRecommends(RecommendType recommendType,int count){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductRecommend.class);
		String alias = getAlias(detachedCriteria , "product");
		 if (alias == null){
			 detachedCriteria.createAlias("product", "product");
		}
		detachedCriteria.add(Restrictions.eq("product.state", ProductStateType.PUBLISH));
		detachedCriteria.add(Restrictions.eq("type", recommendType));
		detachedCriteria.addOrder(Order.desc("startDate"));
		return queryService.findBy(detachedCriteria, 0, count);
	}

	@Transactional(readOnly=true)
	public List<ProductRecommend> findProductRecommends(RecommendType type,
			int start, int limit) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductRecommend.class);
		String alias = getAlias(detachedCriteria , "product");
		 if (alias == null){
			 detachedCriteria.createAlias("product", "product");
		}
		detachedCriteria.add(Restrictions.eq("product.state", ProductStateType.PUBLISH));
		detachedCriteria.add(Restrictions.eq("type", type));
		return queryService.findBy(detachedCriteria, start, limit);
	}

	@Transactional(readOnly=true)
	public long findProductRecommends(RecommendType type) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductRecommend.class);
		String alias = getAlias(detachedCriteria , "product");
		 if (alias == null){
			 detachedCriteria.createAlias("product", "product");
		}
		detachedCriteria.add(Restrictions.eq("product.state", ProductStateType.PUBLISH));
		detachedCriteria.add(Restrictions.eq("type", type));
		return queryService.getRowCount(detachedCriteria);
	}

	/**
	 * 根据产品id，更新 扩展信息的时间
	 */
	@Transactional
	public void updateProductRecommendsByProductId(int productId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductRecommend.class);
		detachedCriteria.add(Restrictions.eq("product.id", productId));
		List<ProductRecommend> productRecommends = queryService.findBy(detachedCriteria);
		Date startDate = new Date();
		for(ProductRecommend productRecommend : productRecommends){
			long diff = startDate.getTime() - productRecommend.getStartDate().getTime();
			int amount = (int)(diff / (1000 * 60 * 60 * 24));
			Date endDate = DateUtils.addDays(startDate, 30-amount);
			productRecommend.setStartDate(startDate);
			productRecommend.setEndDate(endDate);
		}
	}


}
