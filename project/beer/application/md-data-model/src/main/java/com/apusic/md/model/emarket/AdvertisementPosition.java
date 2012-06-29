package com.apusic.md.model.emarket;

import java.util.HashSet;
import java.util.Set;

import com.apusic.ebiz.model.BaseModel;

/**
 * 广告位实体
 * @author xuzhengping
 *
 */
public class AdvertisementPosition extends BaseModel {


	private String position;//广告位置名称

	private String desc;//广告位置描述，eg：放置图片的大写 等

	private int limit;//限制图片的张数

	private Set<Advertisement> advertisements;

	public Set<Advertisement> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	public void addAdvertisement(Advertisement ad){
		if(this.advertisements==null){
			advertisements = new HashSet<Advertisement>();
		}
		advertisements.add(ad);
	}

	public void removeAdvertisement(Advertisement ad){
		if(this.advertisements==null){
			advertisements = new HashSet<Advertisement>();
		}
		advertisements.remove(ad);
	}


	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
