package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.md.model.emarket.AdvertisementPosition;


/**
 * 广告页面管理
 * @author chenwenjie
 *
 */
public interface AdvertisementPageManagementService {
	
	/**
	 * 新增一个广告位置
	 * @param advertisementPosition
	 */
	void addAdvertisementPosition(AdvertisementPosition advertisementPosition);
	
	/**
	 * 修改广告位置
	 * @param advertisementPosition
	 */
	void updateAdvertisementPosition(AdvertisementPosition advertisementPosition);
	
	/**
	 * 删除广告位置
	 * @param id
	 */
	void deleteAdvertisementPosition(Integer id); 
	
	/**
	 * 获取一个广告位置
	 * @param id
	 * @return
	 */
	AdvertisementPosition findAdvertisementPositionById(Integer id);
	
	/**
	 * 获取所有广告页面位置 
	 * @return
	 */
	List<AdvertisementPosition> findAdvertisementPositions();
}
