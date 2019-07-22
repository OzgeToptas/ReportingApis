package com.ozge.service.impl;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ozge.configuration.properties.ReportServiceProperties;
import com.ozge.model.request.RefundsReportRequest;
import com.ozge.model.response.RefundReportResponse;
import com.ozge.service.ReportService;
import com.ozge.util.HttpUtils;

@Service
public class ReportServiceImpl implements ReportService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    private final String url;

    @Autowired
    public ReportServiceImpl(RestTemplate restTemplate, ReportServiceProperties properties) {
        this.restTemplate = restTemplate;
        url = properties.getUrl();
    }

    @Override
    public Optional<RefundReportResponse> getRefundsReport(RefundsReportRequest refundsReportRequest, String authToken) {

        log.info("Getting refunds report service was called -> {} - ( fromDate : {} - toDate : {} - merchant : {} - acquirer : {} ) - token ( {} )",
                url,
                refundsReportRequest.getFromDate(),
                refundsReportRequest.getToDate(),
                refundsReportRequest.getMerchant(),
                refundsReportRequest.getAcquirer(),
                authToken);

        RefundReportResponse refundReportResponse = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(refundsReportRequest, HttpUtils.generateAuthorizationHeader(authToken)), RefundReportResponse.class).getBody();

        return Optional.of(refundReportResponse);

    }

}
