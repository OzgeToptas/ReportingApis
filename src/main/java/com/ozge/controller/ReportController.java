package com.ozge.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ozge.model.error.AuthorizationError;
import com.ozge.model.error.ErrorResponse;
import com.ozge.model.request.RefundsReportRequest;
import com.ozge.service.ReportService;

@RestController
public class ReportController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/refunds/report", produces = "application/json; charset=UTF-8")
	public ResponseEntity refundsReport(@RequestHeader(name = "Authorization", required = false) String authToken,
			@RequestBody @Valid RefundsReportRequest refundsReportRequest, BindingResult bindingResult) {

		log.info(
				"Refunds report request attempt -> ( Report fromDate : {} - toDate : {} - merchant : {} - acquirer : {} ) - Authorization ( {} )",
				refundsReportRequest.getFromDate(), refundsReportRequest.getToDate(),
				refundsReportRequest.getMerchant(), refundsReportRequest.getAcquirer(), authToken);

		if (authToken.isEmpty()) {
			return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Missed!")),
					HttpStatus.UNAUTHORIZED);
		}

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return reportService.getRefundsReport(refundsReportRequest, authToken)
				.map(refundReportResponse -> new ResponseEntity<>(refundReportResponse, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR));

	}

}
