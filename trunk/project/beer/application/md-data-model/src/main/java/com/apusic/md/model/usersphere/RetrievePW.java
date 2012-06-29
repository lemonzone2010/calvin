package com.apusic.md.model.usersphere;

import java.util.Date;

import com.apusic.ebiz.model.BaseModel;

/**
 * 重置密码实体类
 * @author xuzhengping
 *
 */
public class RetrievePW extends BaseModel {


	private String name;

	private String email;

	private String securityCode;

	private String resetCode;

	private Date timeout;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getResetCode() {
		return resetCode;
	}

	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}

	public Date getTimeout() {
		return timeout;
	}

	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}
}
