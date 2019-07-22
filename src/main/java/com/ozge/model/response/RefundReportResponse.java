package com.ozge.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundReportResponse {

	private String status;

	@JsonProperty("response")
	private List<RefundReport> refundReports;

	public RefundReportResponse() {
		super();
	}

	public RefundReportResponse(String status, List<RefundReport> refundReports) {
		super();
		this.status = status;
		this.refundReports = refundReports;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RefundReport> getRefundReports() {
		return refundReports;
	}

	public void setRefundReports(List<RefundReport> refundReports) {
		this.refundReports = refundReports;
	}

}
