package com.apusic.ebiz.framework.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.apusic.ebiz.framework.core.model.IdEntity;

public class QueryObject {

	public QueryObject(Class<? extends IdEntity> clazz) {
		criteria = DetachedCriteria.forClass(clazz);
	}

	private Map<String, Object> parameters = new HashMap<String, Object>();
	private List<String> alias = new ArrayList<String>();
	private DetachedCriteria criteria;

	public void createAlias(String entity, String a) {
		if (!hadAlias(a)) {
			criteria.createAlias(entity, a);
			alias.add(a);
		}
	}

	public Boolean hadAlias(String a) {
		if (alias.isEmpty()) {
			return Boolean.FALSE;
		}
		return alias.contains(a);
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public Object getParameterValue(String key) {
		return parameters.get(key);
	}

	public DetachedCriteria getDetachedCriteria() {
		return criteria;
	}

	private QueryObject addCriterion(Criterion in) {
		criteria.add(in);
		return this;
	}

	@UseProxy
	public QueryObject in(String propertyName, String key) {
		this.addCriterion(Restrictions.in(propertyName, (List<?>) this.getParameterValue(getKey(key))));
		return this;
	}

	@UseProxy
	public QueryObject in(String propertyName, List<?> values) {
		this.addCriterion(Restrictions.in(propertyName, values));
		return this;
	}

	@UseProxy
	public QueryObject between(String propertyName, Object lo, Object hi) {
		Object newLo = null;
		Object newHi = null;
		if (lo instanceof String && this.isKey(lo.toString())) {
			newLo = this.getParameterValue(getKey(lo.toString()));
			newHi = this.getParameterValue(getKey(hi.toString()));
		}
		if (newLo != null && newHi == null) {
			this.addCriterion(Restrictions.between(propertyName, newLo, hi));
			return this;
		}
		if (newLo != null && newHi != null) {
			this.addCriterion(Restrictions.between(propertyName, newLo, newHi));
			return this;
		}
		if (newLo == null && newHi != null) {
			this.addCriterion(Restrictions.between(propertyName, lo, newHi));
			return this;
		}
		this.addCriterion(Restrictions.between(propertyName, lo, hi));
		return this;
	}

	public boolean isKey(String keyOrValue) {
		return StringUtils.startsWith(keyOrValue, "${");
	}

	public String getKey(String psudoKey) {
		String key = StringUtils.left(psudoKey, psudoKey.length() - 1);
		key = StringUtils.right(key, key.length() - 2);
		return key;
	}

	@UseProxy
	public QueryObject eq(String propertyName, String keyOrValue) {
		if (this.isKey(keyOrValue)) {
			this.addCriterion(Restrictions.eq(propertyName, this.getParameterValue(getKey(keyOrValue))));
			return this;
		}
		this.addCriterion(Restrictions.eq(propertyName, keyOrValue));
		return this;
	}

	@UseProxy
	public QueryObject eq(String propertyName, Date date) {
		this.addCriterion(Restrictions.eq(propertyName, date));
		return this;
	}

	@UseProxy
	public QueryObject eq(String propertyName, Object o) {
		this.addCriterion(Restrictions.eq(propertyName, o));
		return this;
	}

	@UseProxy()
	public QueryObject isEmpty(String propertyName) {
		this.addCriterion(Restrictions.isEmpty(propertyName));
		return this;
	}

	@UseProxy
	public QueryObject isNotEmpty(String propertyName) {

		this.addCriterion(Restrictions.isNotEmpty(propertyName));
		return this;
	}

	@UseProxy
	public QueryObject isNotNull(String propertyName) {

		this.addCriterion(Restrictions.isNotNull(propertyName));
		return this;
	}

	@UseProxy
	public QueryObject isNull(String propertyName) {

		this.addCriterion(Restrictions.isNull(propertyName));
		return this;
	}

	@UseProxy
	public QueryObject le(String propertyName, String keyOrValue) {
		if (this.isKey(keyOrValue)) {
			this.addCriterion(Restrictions.le(propertyName, this.getParameterValue(getKey(keyOrValue))));
			return this;
		}
		this.addCriterion(Restrictions.le(propertyName, keyOrValue));
		return this;
	}

	@UseProxy
	public QueryObject ge(String propertyName, String keyOrValue) {
		if (this.isKey(keyOrValue)) {
			this.addCriterion(Restrictions.ge(propertyName, this.getParameterValue(getKey(keyOrValue))));
			return this;
		}
		this.addCriterion(Restrictions.ge(propertyName, keyOrValue));
		return this;
	}

	@UseProxy
	public QueryObject ne(String propertyName, String keyOrValue) {
		if (this.isKey(keyOrValue)) {
			this.addCriterion(Restrictions.ne(propertyName, this.getParameterValue(getKey(keyOrValue))));
			return this;
		}

		this.addCriterion(Restrictions.ne(propertyName, keyOrValue));
		return this;
	}

	@UseProxy
	public QueryObject like(String propertyName, String keyOrValue, MatchMode matchMode) {
		if (this.isKey(keyOrValue)) {
			this.addCriterion(Restrictions.like(propertyName, (String) this.getParameterValue(getKey(keyOrValue)),
					matchMode));
			return this;
		}
		this.addCriterion(Restrictions.like(propertyName, keyOrValue, matchMode));
		return this;
	}

	@UseProxy
	public QueryObject lt(String propertyName, String keyOrValue) {
		if (this.isKey(keyOrValue)) {
			this.addCriterion(Restrictions.lt(propertyName, this.getParameterValue(getKey(keyOrValue))));
			return this;
		}
		this.addCriterion(Restrictions.lt(propertyName, keyOrValue));
		return this;
	}

	@UseProxy
	public QueryObject gt(String propertyName, String keyOrValue) {
		if (this.isKey(keyOrValue)) {
			this.addCriterion(Restrictions.gt(propertyName, this.getParameterValue(getKey(keyOrValue))));
			return this;
		}
		this.addCriterion(Restrictions.gt(propertyName, keyOrValue));
		return this;
	}

}