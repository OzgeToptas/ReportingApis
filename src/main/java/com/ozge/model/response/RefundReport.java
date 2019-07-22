package com.ozge.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundReport {

	private Integer count;

	private Long total;

	private String currency;

	public RefundReport() {
		super();
	}

	public RefundReport(Integer count, Long total, String currency) {
		super();
		this.count = count;
		this.total = total;
		this.currency = currency;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
