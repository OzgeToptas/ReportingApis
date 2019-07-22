package com.ozge.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "report-refunds")
public class ReportServiceProperties {

    String url;
    
    public ReportServiceProperties() {
		super();
	}

	public ReportServiceProperties(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
