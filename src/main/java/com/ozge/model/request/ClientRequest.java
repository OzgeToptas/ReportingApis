package com.ozge.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRequest {

    @NotBlank(message = "Transaction Id cannot be blank")
    private String transactionId;

	public ClientRequest() {
		super();
	}

	public ClientRequest(@NotBlank(message = "Transaction Id cannot be blank") String transactionId) {
		super();
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public static Object builder() {
		return null;
	}

	
}
