package com.apusic.md.usersphere.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.md.model.usersphere.RetrievePW;

@Repository("usersphere_RetrevePwDao")
public class RetrievePwDaoImpl implements RetrievePwDao {

	@Autowired
	private QueryService queryService;

	public RetrievePW getRetrievePWByName(String name) {
		RetrievePW p = new RetrievePW();
		p.setName(name);
		List<RetrievePW> rs = queryService.findByExample(p);
		if(rs!=null && rs.size()>0){
			return rs.get(0);
		}
		return null;
	}

}
