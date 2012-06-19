package org.jasig.cas.adaptors.jdbc;

import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.UserDisabledException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class QueryDatabaseForPasswordAndDisabledQuthenticationHandler extends
		AbstractJdbcUsernamePasswordAuthenticationHandler {

	@NotNull
	private String sql;

	@NotNull
	private String disabledSql;

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setDisabledSql(String disabledSql) {
		this.disabledSql = disabledSql;
	}

	@Override
	protected boolean authenticateUsernamePasswordInternal(
			UsernamePasswordCredentials credentials)
			throws AuthenticationException {
		 final String username = getPrincipalNameTransformer().transform(credentials.getUsername());
	        final String password = credentials.getPassword();
	        final String encryptedPassword = this.getPasswordEncoder().encode(
	            password);
	        try {
	        	final String dbPassword = getJdbcTemplate().queryForObject(
	                this.sql, String.class, username);
	        	if(dbPassword!=null){
	        		final Boolean disabled = getJdbcTemplate().queryForObject(this.disabledSql, Boolean.class, username);
	        		if(!disabled){
	        			throw new UserDisabledException();
	        		}
	        	}
	            return dbPassword.equals(encryptedPassword);
	        } catch (final IncorrectResultSizeDataAccessException e) {
	            // this means the username was not found.
	            return false;
	        }
	}
}
