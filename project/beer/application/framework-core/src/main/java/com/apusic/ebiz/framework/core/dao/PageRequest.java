package com.apusic.ebiz.framework.core.dao;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页参数封装类.
 */
public class PageRequest {
	private static final int DEFAULT_PAGE_SIZE=20;
	protected int pageNo = 1;
	protected int pageSize = DEFAULT_PAGE_SIZE;

	protected String orderBy = null;
	protected String orderDirect = null;//asc,desc

	protected boolean countTotal = true;

	public PageRequest() {
	}

	public PageRequest(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * 获得当前页的页号, 序号从1开始, 默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号, 序号从1开始, 低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量, 默认为10.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量, 低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = DEFAULT_PAGE_SIZE;
		}
	}

	/**
	 * 获得排序字段, 无默认值. 多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orderDirect));
	}

	/**
	 * 是否默认计算总记录数.
	 */
	public boolean isCountTotal() {
		return countTotal;
	}

	/**
	 * 设置是否默认计算总记录数.
	 */
	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置, 序号从0开始.
	 */
	public int getOffset() {
		return ((pageNo - 1) * pageSize);
	}

	public String getOrderDirect() {
		return orderDirect;
	}

	public void setOrderDirect(String orderDirect) {
		this.orderDirect = orderDirect;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
