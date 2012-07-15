package com.apusic.md.emarket.service;

import java.util.List;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.emarket.Advertisement;

/**
 * 广告发布管理
 * @author chenwenjie
 *
 */
public interface AdvertisementManagementService {

	/**
	 * 新增一个广告
	 * @param advertisement
	 */
	void addAdvertisement(Advertisement advertisement);

	/**
	 * 修改广告
	 * @param advertisement
	 */
	void updateAdvertisement(Advertisement advertisement);

	/**
	 * 删除广告
	 * @param id
	 */
	void deleteAdvertisement(Integer id);

	/**
	 * 获取一个广告
	 * @param id
	 * @return
	 */
	Advertisement findAdvertisementById(Integer id);

	/**
	 * 根据商家名称模糊查询
	 * @return
	 */
	Page<Advertisement> findLikeAdvertisementsByStoreName4Page(GenericQueryObject query);

	/**
	 * 获取所有广告
	 * @return
	 */
	Page<Advertisement> findAdvertisements4Page();

	/**
	 * 根据广告位获取广告
	 * @param positionName
	 * @return
	 */
	List<Advertisement> findAdsByPosition(String positionName);

	/**
	 * 根据广告位获取最新广告
	 * @param string
	 * @return
	 */
	Advertisement findFirstAdByPosition(String positionName);
}
