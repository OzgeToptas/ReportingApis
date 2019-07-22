package com.ozge.model.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundsReportRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "From Date cannot be null")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "To Date cannot be null")
    private Date toDate;
    
    private Integer merchant;

    private Integer acquirer;
    
    
	public RefundsReportRequest() {
		super();
	}

	public RefundsReportRequest(@NotNull(message = "From Date cannot be null") Date fromDate,
			@NotNull(message = "To Date cannot be null") Date toDate, Integer merchant, Integer acquirer) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.merchant = merchant;
		this.acquirer = acquirer;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getMerchant() {
		return merchant;
	}

	public void setMerchant(Integer merchant) {
		this.merchant = merchant;
	}

	public Integer getAcquirer() {
		return acquirer;
	}

	public void setAcquirer(Integer acquirer) {
		this.acquirer = acquirer;
	}

}
