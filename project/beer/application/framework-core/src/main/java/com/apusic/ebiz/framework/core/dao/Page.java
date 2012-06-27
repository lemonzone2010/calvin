package com.apusic.ebiz.framework.core.dao;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 与具体ORM实现无关的分页查询结果封
 * 
 * @param <T>
 *            Page中记录的类型.
 * 
 * @author calvin
 */
public class Page<T> extends PageRequest implements Iterable<T> {

	protected List<T> rows = null;
	protected long total = 0;
	private String q;// property:value,后期可能为value,可设置默认查询条�?查询条件可为ALL？？�?

	public Page() {
	}

	public Page(PageRequest request) {
		this.pageNo = request.pageNo;
		this.pageSize = request.pageSize;
		this.countTotal = request.countTotal;
		this.orderBy = request.orderBy;
		this.orderDirect = request.orderDirect;
	}

	/**
	 * 实现Iterable接口, 可以for(Object item : page)遍历使用
	 */
	@Override
	public Iterator<T> iterator() {
		return rows.iterator();
	}

	/**
	 * 根据pageSize与totalItems计算总页�?
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double) total / (double) getPageSize());

	}

	/**
	 * 是否还有下一�?
	 */
	public boolean hasNextPage() {
		return (getPageNo() + 1 <= getTotalPages());
	}

	/**
	 * 是否�?���?��.
	 */
	public boolean isLastPage() {
		return !hasNextPage();
	}

	/**
	 * 取得下页的页�? 序号�?�?��. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (hasNextPage()) {
			return getPageNo() + 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * 是否还有上一�?
	 */
	public boolean hasPrePage() {
		return (getPageNo() > 1);
	}

	/**
	 * 是否第一�?
	 */
	public boolean isFirstPage() {
		return !hasPrePage();
	}

	/**
	 * 取得上页的页�? 序号�?�?��. 当前页为首页时返回首页序�?
	 */
	public int getPrePage() {
		if (hasPrePage()) {
			return getPageNo() - 1;
		} else {
			return getPageNo();
		}
	}

	/**
	 * 计算以当前页为中心的页面列表,�?首页,23,24,25,26,27,末页"
	 * 
	 * @param count
	 *            �?��计算的列表大�?
	 * @return pageNo列表
	 */
	public List<Integer> getSlider(int count) {
		int halfSize = count / 2;
		int totalPage = getTotalPages();

		int startPageNo = Math.max(getPageNo() - halfSize, 1);
		int endPageNo = Math.min(startPageNo + count - 1, totalPage);

		if (endPageNo - startPageNo < count) {
			startPageNo = Math.max(endPageNo - count, 1);
		}

		List<Integer> result = Lists.newArrayList();
		for (int i = startPageNo; i <= endPageNo; i++) {
			result.add(i);
		}
		return result;
	}

	public List<T> getRows() {
		return rows;
	}
	@JsonIgnore
	public List<T> getObjects() {
		return rows;
	}

	public Page<T> setRows(List<T> rows) {
		this.rows = rows;
		return this;
	}

	/**
	 * @return 总记录条数
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total 总记录条数
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 从requestMap中得到需要的参数,主要用于查询
	 * 
	 * @param request
	 */
	public Page<T> setRequestMap(Map<String, String[]> requestMap) {
		setPageSize(getFirstValueInteger(requestMap.get("rows")));
		setPageNo(getFirstValueInteger(requestMap.get("page")));
		setOrderBy((getFirstValue(requestMap.get("sort"))));
		setOrderDirect((getFirstValue(requestMap.get("order"))));
		setQ((getFirstValue(requestMap.get("q"))));
		return this;
	}

	private String getFirstValue(String[] in) {
		if(null==in || in.length<1)return "";
		String tmp = "";
		try {
			tmp = isUTF8(in[0].getBytes())?in[0]:new String(in[0].getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	private Integer getFirstValueInteger(String[] in) {
		String value = getFirstValue(in);
		if (StringUtils.isEmpty(value)) {
			return 0;
		}
		return Integer.valueOf(value);
	}

	public String getQ() {
		return q;
	}
	 public static boolean isUTF8(byte[] file){
	        if(file.length<3)
	            return false;
	        if((file[0]&0xFF)==0xEF && 
	                (file[1]&0xFF)==0xBB &&
	                (file[2]&0xFF)==0xBF)
	            return true;
	        return false;
	    }
	public void setQ(String q) {
		this.q = q;
	}

	public Map<String, String> getQMap() {
		Map<String, String> ret = Maps.newHashMap();
		if (StringUtils.isNotBlank(q)) {
			String kv[] = q.split(":");
			if (kv.length < 2) {
				return ret;
			}
			ret.put(kv[0], kv[1]);
		}
		return ret;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [q=");
		builder.append(q);
		builder.append(", rows=");
		builder.append(rows);
		builder.append(", total=");
		builder.append(total);
		builder.append(", countTotal=");
		builder.append(countTotal);
		builder.append(", orderBy=");
		builder.append(orderBy);
		builder.append(", orderDirect=");
		builder.append(orderDirect);
		builder.append(", pageNo=");
		builder.append(pageNo);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append("]");
		return builder.toString();
	}

	
}
