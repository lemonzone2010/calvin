package com.apusic.md.usersphere.dao;

import java.util.List;

import com.apusic.md.model.usersphere.DeliveryAddr;

public interface DeliveryAddrDao {

	long getDeliveryAddrCount(String userName);

	List<DeliveryAddr> getDeliveryAddrsByUserName(String userName);

	DeliveryAddr getDefaultAddrByUserName(String userName);

}
