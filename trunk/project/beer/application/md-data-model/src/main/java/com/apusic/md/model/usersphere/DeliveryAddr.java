package com.apusic.md.model.usersphere;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.ebiz.model.user.User;

public class DeliveryAddr extends BaseModel {


	/***收货人*/
	private String consignee;

	private String province;

	private String city;

	private String area;

	private String streetAddr;

	private String postcode;

	/***手机*/
	private String mobile;

	/***固定电话*/
	private String phone;

	private int isDefault;

	private User user;


	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		if(area == null){
			return "";
		}
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreetAddr() {
		return streetAddr;
	}

	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAddress(){
		StringBuffer buffer = new StringBuffer();
		if(province != null){
			buffer.append(province);
		}
		if(city != null){
			buffer.append(city);
		}
		if(area != null){
			buffer.append(area);
		}
		if(streetAddr != null){
			buffer.append(streetAddr);
		}
		return buffer.toString();
	}
}
