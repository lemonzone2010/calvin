package com.apusic.md.emarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.emarket.AdvertisementPosition;

@Service("emarket_AdvertisementPageManagementService")
public class AdvertisementPageManagementServiceImpl implements
		AdvertisementPageManagementService {
	
	@Autowired
	private CrudService crudService;
	
	@Autowired
	private QueryService queryService;

	@Transactional
	public void addAdvertisementPosition(
			AdvertisementPosition advertisementPosition) {
		this.crudService.create(advertisementPosition);
	}
	
	@Transactional
	public void updateAdvertisementPosition(
			AdvertisementPosition advertisementPosition) {
		this.crudService.update(advertisementPosition);
	}

	@Transactional
	public void deleteAdvertisementPosition(Integer id) {
		this.crudService.delete(AdvertisementPosition.class,id);
	}

	public AdvertisementPosition findAdvertisementPositionById(Integer id) {
		return crudService.retrieve(AdvertisementPosition.class, id);
	}

	public List<AdvertisementPosition> findAdvertisementPositions() {
		return queryService.findAll(AdvertisementPosition.class);
	}

}
