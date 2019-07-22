package com.ozge.service;

import java.util.Optional;

import com.ozge.model.request.RefundsReportRequest;
import com.ozge.model.response.RefundReportResponse;

public interface ReportService {

    Optional<RefundReportResponse> getRefundsReport(RefundsReportRequest refundsReportRequest, String authToken);

}
