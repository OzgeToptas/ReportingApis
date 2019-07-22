package com.ozge.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "merchant-user")
public class UserServiceProperties {

	private Path login;

	private Path info;

	public UserServiceProperties() {
		super();
	}

	public UserServiceProperties(Path login, Path info) {
		super();
		this.login = login;
		this.info = info;
	}

	public Path getLogin() {
		return login;
	}

	public void setLogin(Path login) {
		this.login = login;
	}

	public Path getInfo() {
		return info;
	}

	public void setInfo(Path info) {
		this.info = info;
	}

}
