package com.apusic.ebiz.smartorg.dao;

import java.util.List;

import com.apusic.ebiz.model.user.Resource;

public interface ResourceDao {

	Resource getResourceByContextAndValue(String context, String value);

	List<Resource> findresourceByRoleId(int id);
}
