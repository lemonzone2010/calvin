package com.apusic.md.usersphere.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.DeliveryAddr;
import com.apusic.md.usersphere.UsersphereException;
import com.apusic.md.usersphere.dao.DeliveryAddrDao;


@Service("usersphere_DeliveryAddrService")
public class DeliveryAddrServiceImpl implements DeliveryAddrService {

	@Autowired
	@Value(value = "5")
	private int deliveryAddrConut;

	@Autowired
	private CrudService crudService;

	@Autowired
	private DeliveryAddrDao deliveryAddrDao;

	@Autowired
	private UserService userService;

	@Transactional
	public void addDeliveryAddr(DeliveryAddr deliveryAddr, String userName,
			boolean isDefault) {
		User user = userService.getUserByName(userName);
		if (user == null) {
			throw new UsersphereException("userName=" + userName
					+ "do not exist");
		}
		if(isMaxCount(user.getName())){
			throw new UsersphereException("地址已经达到最大个数");
		}

		deliveryAddr.setUser(user);
		deliveryAddr.setIsDefault(0);
		crudService.create(deliveryAddr);
		if (isDefault) {
			// 如果需要设置为默认
			changeDefault(userName, deliveryAddr.getId());
		}
	}


	public boolean isMaxCount(String userName) {
		long count = deliveryAddrDao.getDeliveryAddrCount(userName);
		if (count >= deliveryAddrConut) {
			return true;
		}
		return false;
	}


	@Transactional
	public void changeDefault(String userName,int  addrId) {
		DeliveryAddr addr=getDeliveryAddrById(addrId);
		List<DeliveryAddr> addrs=getDeliveryAddrsByUserName(userName);
		for (DeliveryAddr item : addrs) {
			if(item.getId()==addr.getId()){
				addr.setIsDefault(1);
				crudService.update(addr);
				continue;
			}
			item.setIsDefault(0);
			crudService.update(item);
		}
	}

	public List<DeliveryAddr> getDeliveryAddrsByUserName(String userName) {
		return deliveryAddrDao.getDeliveryAddrsByUserName(userName);
	}

	public DeliveryAddr getDeliveryAddrById(int id) {
		return crudService.retrieve(DeliveryAddr.class, id);
	}
	@Transactional
	public void updateDeliveryAddr(DeliveryAddr deliveryAddr,String userName) {

		User user = userService.getUserByName(userName);

		if(deliveryAddr.getIsDefault()==1){
			changeDefault(deliveryAddr.getUser().getName(), deliveryAddr.getId());
		}else{
			DeliveryAddr oldAddr=getDeliveryAddrById(deliveryAddr.getId());
			if(oldAddr.getUser().getId()!=user.getId()){
				throw new UsersphereException("该收货地址不属于您");
			}
			if(oldAddr!=null&&oldAddr.getIsDefault()==1){
				deliveryAddr.setIsDefault(1);
			}
		}
		deliveryAddr.setUser(user);
		crudService.update(deliveryAddr);

	}
	@Transactional
	public void deleteDeliveryAddr(int id) {
		DeliveryAddr deliveryAddr = getDeliveryAddrById(id);
		crudService.delete(deliveryAddr);
	}

	public DeliveryAddr getDefalutAddrByUserName(String userName) {
		// TODO Auto-generated method stub
		return deliveryAddrDao.getDefaultAddrByUserName(userName);
	}

}
