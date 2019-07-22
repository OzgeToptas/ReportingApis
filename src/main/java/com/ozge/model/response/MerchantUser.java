package com.ozge.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantUser {

	private Integer id;

	private String role;

	private String email;

	private String name;

	private Integer merchantId;

	private String secretKey;

	public MerchantUser() {
		super();
	}

	public MerchantUser(Integer id, String role, String email, String name, Integer merchantId, String secretKey) {
		super();
		this.id = id;
		this.role = role;
		this.email = email;
		this.name = name;
		this.merchantId = merchantId;
		this.secretKey = secretKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
