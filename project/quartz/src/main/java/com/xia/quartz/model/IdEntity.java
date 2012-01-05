package com.xia.quartz.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * 统一定义id的entity基类
 * <table summary="Built-in constraints" border="1"><colgroup><col align="center" width="25%"><col width="25%"><col width="25%"><col width="25%"></colgroup><thead><tr><th align="center">
							Annotation
						</th><th>
							Apply on
						</th><th>
							Runtime checking
						</th><th>
							Hibernate Metadata impact
						</th></tr></thead><tbody><tr><td align="center">
							@Length(min=, max=)
						</td><td>
							property (String)
						</td><td>
							check if the string length match the range
						</td><td>
							Column length will be set to max
						</td></tr><tr><td align="center">
							@Max(value=)
						</td><td>
							property (numeric or string representation of a numeric)
						</td><td>
							check if the value is less than or equals to max
						</td><td>
							Add a check constraint on the column
						</td></tr><tr><td align="center">
							@Min(value=)
						</td><td>
							property (numeric or string representation of a numeric)
						</td><td>
							check if the value is more than or equals to min
						</td><td>
							Add a check constraint on the column
						</td></tr><tr><td align="center">
							@NotNull
						</td><td>
							property
						</td><td>
							check if the value is not null
						</td><td>
							Column(s) are not null
						</td></tr><tr><td align="center">
							@NotEmpty
						</td><td>
							property
						</td><td>
							check if the string is not null nor empty. Check if the connection is not null nor empty
						</td><td>
							Column(s) are not null (for String)
						</td></tr><tr><td align="center">
							@Past
						</td><td>
							property (date or calendar)
						</td><td>
							check if the date is in the past
						</td><td>
							Add a check constraint on the column
						</td></tr><tr><td align="center">
							@Future
						</td><td>
							property (date or calendar)
						</td><td>
							check if the date is in the future
						</td><td>
							none
						</td></tr><tr><td align="center">
							@Pattern(regex="regexp", flag=) or @Patterns( {@Pattern(...)} )
						</td><td>
							property (string)
						</td><td>
							check if the property match the regular expression given a match flag (see <code class="classname">java.util.regex.Pattern </code> )
						</td><td>
							none
						</td></tr><tr><td align="center">
							@Range(min=, max=)
						</td><td>
							property (numeric or string representation of a numeric)
						</td><td>
							check if the value is between min and max (included)
						</td><td>
							Add a check constraint on the column
						</td></tr><tr><td align="center">
							@Size(min=, max=)
						</td><td>
							property (array, collection, map)
						</td><td>
							check if the element size is between min and max (included)
						</td><td>
							none
						</td></tr><tr><td align="center">
							@AssertFalse
						</td><td>
							property
						</td><td>
							check that the method evaluates to false (useful for constraints expressed in code rather than annotations)
						</td><td>
							none
						</td></tr><tr><td align="center">
							@AssertTrue
						</td><td>
							property
						</td><td>
							check that the method evaluates to true (useful for constraints expressed in code rather than annotations)
						</td><td>
							none
						</td></tr><tr><td align="center">
							@Valid
						</td><td>
							property (object)
						</td><td>
							perform validation recursively on the associated object. If the object is a Collection or an array, the elements are validated recursively. If the object is a Map, the value elements are validated recursively.
						</td><td>
							none
						</td></tr><tr><td align="center">
							@Email
						</td><td>
							property (String)
						</td><td>
							check whether the string is conform to the email address specification
						</td><td>
							none
						</td></tr><tr><td align="center">
							@CreditCardNumber
						</td><td>
							property (String)
						</td><td>
							check whether the string is a well formated credit card number (derivative of the Luhn algorithm)
						</td><td>
							none
						</td></tr><tr><td align="center">
							@Digits(integerDigits=1)
						</td><td>
							property (numeric or string representation of a numeric)
						</td><td>
							check whether the property is a number having up to <code class="literal">integerDigits</code> integer digits and <code class="literal">fractionalDigits</code> fractonal digits
						</td><td>
							define column precision and scale
						</td></tr><tr><td align="center">
							@EAN
						</td><td>
							property (string)
						</td><td>
							check whether the string is a properly formated EAN or UPC-A code
						</td><td>
							none
						</td></tr></tbody></table>
 * @author calvin
 */
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" })
@MappedSuperclass
public abstract class IdEntity implements Serializable {
	/**
    *
    */
	private static final long serialVersionUID = 2670138237353648210L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		return (this.getClass().hashCode() * ((null == getId()) ? 0 : getId().intValue())) * prime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IdEntity other = (IdEntity) obj;
		if (null == getId() || getId() == 0) {
			return false;// 特殊处理,只要有一个Id�?，都认为是不相等
		} else if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Transient
	String __errorMessage="";
	public IdEntity validate(Validator validator) {
		__errorMessage = "";
		Set<ConstraintViolation<IdEntity>> failures = validator.validate(this);
		if (!failures.isEmpty()) {
			for (ConstraintViolation<IdEntity> failure : failures) {
				String message = failure.getPropertyPath().toString();
				__errorMessage += (message + " " + failure.getMessage())+"\\n";
			}
		}
		if (StringUtils.isNotBlank(__errorMessage)) {
			throw new RuntimeException(__errorMessage);
		}
		return this;
	}
}
