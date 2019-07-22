package com.ozge.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantUserInfoResponse {

    private String status;

    private MerchantUser merchantUser;
    
	public MerchantUserInfoResponse() {
		super();
	}

	public MerchantUserInfoResponse(String status, MerchantUser merchantUser) {
		super();
		this.status = status;
		this.merchantUser = merchantUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MerchantUser getMerchantUser() {
		return merchantUser;
	}

	public void setMerchantUser(MerchantUser merchantUser) {
		this.merchantUser = merchantUser;
	}

}
