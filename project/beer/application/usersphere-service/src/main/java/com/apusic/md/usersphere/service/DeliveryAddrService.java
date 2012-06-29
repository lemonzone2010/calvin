package com.apusic.md.usersphere.service;

import java.util.List;

import com.apusic.md.model.usersphere.DeliveryAddr;

public interface DeliveryAddrService {

	void addDeliveryAddr(DeliveryAddr deliveryAddr,String userName,boolean isDefault);

	void updateDeliveryAddr(DeliveryAddr deliveryAddr,String userName);

	void deleteDeliveryAddr(int id);

	DeliveryAddr getDeliveryAddrById(int id);

	DeliveryAddr getDefalutAddrByUserName(String userName);

	List<DeliveryAddr> getDeliveryAddrsByUserName(String userName);

	void changeDefault(String userName, int id );
	/**
	 * 是否已经达到最大数量，达到返回true
	 * @param userName
	 * @return
	 */
	boolean isMaxCount(String userName);
}
