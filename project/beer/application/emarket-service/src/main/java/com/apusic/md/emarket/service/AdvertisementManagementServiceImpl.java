package com.apusic.md.emarket.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
import com.apusic.md.model.emarket.Advertisement;

@Service("emarket_AdvertisementManagementService")
public class AdvertisementManagementServiceImpl implements
		AdvertisementManagementService {

	@Autowired
	private CrudService crudService;

	@Autowired
	private QueryService queryService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Transactional
	public void addAdvertisement(Advertisement advertisement) {
		this.crudService.create(advertisement);
	}

	@Transactional
	public void updateAdvertisement(Advertisement advertisement) {
		this.crudService.update(advertisement);
	}

	@Transactional
	public void deleteAdvertisement(Integer id) {
		this.crudService.delete(Advertisement.class,id);
	}

	public Advertisement findAdvertisementById(Integer id) {
		return crudService.retrieve(Advertisement.class, id);
	}

	public Page<Advertisement> findAdvertisements4Page() {
		return queryService.findAllPage(Advertisement.class, configService
				.getIntegerValueByKey("pageSize"));
	}

	public Page<Advertisement> findLikeAdvertisementsByStoreName4Page(GenericQueryObject query) {
		return queryService.findPageBy(query.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	public List<Advertisement> findAdsByPosition(String positionName) {
		DetachedCriteria c = DetachedCriteria.forClass(Advertisement.class);
		c.createAlias("position", "position");
		c.add(Restrictions.eq("position.position",positionName));
		c.addOrder(Order.asc("serialNumber"));
		return queryService.findBy(c);
	}

	public Advertisement findFirstAdByPosition(String positionName) {
		List<Advertisement> ads= findAdsByPosition( positionName);
		if (ads.size()>0) {
			return ads.get(0);
		}
		return null;
	}

}
