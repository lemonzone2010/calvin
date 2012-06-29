package com.apusic.md.model.emarket;

import java.util.Date;

import com.apusic.ebiz.model.BaseModel;

public class ProductRecommend extends BaseModel{

    private Product product;

    private Integer serialNumber;

    private Date startDate;

    private Date endDate;

    private RecommendType type;




    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RecommendType getType() {
        return type;
    }

    public void setType(RecommendType type) {
        this.type = type;
    }
}
