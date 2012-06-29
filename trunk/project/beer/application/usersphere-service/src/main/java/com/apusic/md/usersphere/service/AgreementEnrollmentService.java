package com.apusic.md.usersphere.service;

import com.apusic.md.model.usersphere.AgreementType;
import com.apusic.md.model.usersphere.RegisterAgreement;

public interface AgreementEnrollmentService {

	/**
	 * 添加一个注册协议
	 * @param registerAgreement
	 */
	void addRegisterAgreement(RegisterAgreement registerAgreement);

	/**
	 * 删除一个注册协议
	 * @param id
	 */
	void deleteRegisterAgreement(int id);

	/**
	 * 更新一个注册协议
	 * @param registerAgreement
	 */
	void updateRegisterAgreement(RegisterAgreement registerAgreement);

	/**
	 * 根据id获取协议
	 * @param id
	 */
	RegisterAgreement getRegisterAgreement(int id);

	/**
	 * 更加协议类型获取协议
	 * @param type
	 */
	RegisterAgreement getRegisterAgreement(AgreementType type);
}
