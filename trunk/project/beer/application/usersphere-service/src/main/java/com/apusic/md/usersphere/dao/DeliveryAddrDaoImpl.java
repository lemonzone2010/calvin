package com.apusic.md.usersphere.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.usersphere.DeliveryAddr;

@Repository("usersphere_DeliveryAddrDao")
public class DeliveryAddrDaoImpl implements DeliveryAddrDao {

	@Autowired
	private QueryService queryService;

	public long getDeliveryAddrCount(String userName) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(DeliveryAddr.class);
		criteria.createCriteria("user").add(Restrictions.eq("name", userName));
		long rowCount = queryService.getRowCount(criteria);
		return rowCount;
	}

	public List<DeliveryAddr> getDeliveryAddrsByUserName(String userName) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(DeliveryAddr.class);
		// Restrictions.
		criteria.createCriteria("user").add(Restrictions.eq("name", userName));
		List<DeliveryAddr> deliveryAddrs = queryService
				.findBy(criteria);
		return deliveryAddrs;

	}

	public DeliveryAddr getDefaultAddrByUserName(String userName) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(DeliveryAddr.class);
		criteria.add(Restrictions.gt("isDefault", 0));
		criteria.createCriteria("user").add(Restrictions.eq("name", userName));
		List<DeliveryAddr> deliveryAddrs = queryService
				.findBy(criteria);
		if (deliveryAddrs != null && deliveryAddrs.size() > 0) {
			return deliveryAddrs.get(0);
		}
		return null;
	}



}
