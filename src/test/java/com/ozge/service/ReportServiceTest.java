package com.ozge.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.ozge.configuration.properties.ReportServiceProperties;
import com.ozge.model.request.RefundsReportRequest;
import com.ozge.model.response.RefundReport;
import com.ozge.model.response.RefundReportResponse;
import com.ozge.service.impl.ReportServiceImpl;
import com.ozge.util.BaseTestCase;
import com.ozge.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest extends BaseTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private RestTemplate restTemplateMock;

    private ReportService reportService;

    private final ReportServiceProperties properties = new ReportServiceProperties("https://sandbox-reporting.rpdpymnt.com/api/v3/refunds/report");

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl(restTemplateMock, properties);
    }

    @Test
    public void getRefundsReportWithValidRefundsReportRequestAndAuthorizationTokenShouldReturnRefundsReportResponse() throws Exception {
        // GIVEN
        final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJhZG1pbiIsIm1lcmNoYW50SWQiOjMsInN1Yk1lcmNoYW50SWRzIjpbMyw3NCw5MywxMTkxLDExMSwxMzcsMTM4LDE0MiwxNDUsMTQ2LDE1MywzMzQsMTc1LDE4NCwyMjAsMjIxLDIyMiwyMjMsMjk0LDMyMiwzMjMsMzI3LDMyOSwzMzAsMzQ5LDM5MCwzOTEsNDU1LDQ1Niw0NzksNDg4LDU2MywxMTQ5LDU3MCwxMTM4LDExNTYsMTE1NywxMTU4LDExNzldLCJ0aW1lc3RhbXAiOjE1MDQxMDg3NzN9.Jt5JVXoEEkck4M9fbmDOaykhMpoq-x-D40rY-7Hv_fQ";

        HttpHeaders headers = TestUtils.generateAuthorizationHeader(authToken);

        RefundsReportRequest refundsReportRequest = new RefundsReportRequest(); 
        		
        refundsReportRequest.setFromDate(Calendar.getInstance().getTime());
        refundsReportRequest.setToDate(Calendar.getInstance().getTime());
        refundsReportRequest.setMerchant(2);
        refundsReportRequest.setAcquirer(5);

        RefundReport refundReport = new RefundReport();
        refundReport.setCount(283);
        refundReport.setTotal((long) 28300);
        refundReport.setCurrency("USD");

        RefundReport refundReport2 = new RefundReport();
        refundReport2.setCount(11);
        refundReport2.setTotal((long) 110);
        refundReport2.setCurrency("EUR");

        RefundReportResponse refundReportResponse = new RefundReportResponse();
        refundReportResponse.setStatus("APPROVED");
        refundReportResponse.setRefundReports(Arrays.asList(refundReport, refundReport2));

        // WHEN
        when(restTemplateMock.exchange(properties.getUrl(), HttpMethod.POST, new HttpEntity<>(refundsReportRequest, headers), RefundReportResponse.class))
                .thenReturn(new ResponseEntity<>(refundReportResponse, HttpStatus.OK));

        Optional<RefundReportResponse> optional = reportService.getRefundsReport(refundsReportRequest, authToken);

        // THEN
        verify(restTemplateMock, times(1)).exchange(properties.getUrl(), HttpMethod.POST, new HttpEntity<>(refundsReportRequest, headers), RefundReportResponse.class);
        assertTrue("Fault [expected true]", optional.isPresent());
        assertEquals("Fault [expected 'Status' equals]",
                "APPROVED",
                optional.get().getStatus());
        assertEquals("Fault [expected 'Refunds Report Size' equals]",
                2,
                optional.get().getRefundReports().size());
        assertEquals("Fault [expected 'First Refunds Report Currency Type' equals]",
                "USD",
                optional.get().getRefundReports().get(0).getCurrency());
        assertEquals("Fault [expected 'Second Refunds Report Total Amount' equals]",
                Long.valueOf(110),
                optional.get().getRefundReports().get(1).getTotal());
    }

    @Test
    public void getRefundsReportWithInvalidAuthorizationTokenShouldThrowUnauthorizedException() throws Exception {
        // GIVEN
        final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJhZG1pbiIsIm1lcmNoYW50SWQiOjMsInN1Yk1lcmNoYW50SWRzIjpbMyw3NCw5MywxMTkxLDExMSwxMzcsMTM4LDE0MiwxNDUsMTQ2LDE1MywzMzQsMTc1LDE4NCwyMjAsMjIxLDIyMiwyMjMsMjk0LDMyMiwzMjMsMzI3LDMyOSwzMzAsMzQ5LDM5MCwzOTEsNDU1LDQ1Niw0NzksNDg4LDU2MywxMTQ5LDU3MCwxMTM4LDExNTYsMTE1NywxMTU4LDExNzldLCJ0aW1lc3RhbXAiOjE1MDQxMDg3NzN9.Jt5JVXoEEkck4M9fbmDOaykhMpoq-x-D40rY-7Hv_fQ";
        final String expectedExceptionMessage = "401 UNAUTHORIZED";

        HttpHeaders headers = TestUtils.generateAuthorizationHeader(authToken);

        RefundsReportRequest refundsReportRequest = new RefundsReportRequest(); 
        
        refundsReportRequest.setFromDate(Calendar.getInstance().getTime());
        refundsReportRequest.setToDate(Calendar.getInstance().getTime());
        refundsReportRequest.setMerchant(2);
        refundsReportRequest.setAcquirer(5);

        // WHEN
        when(restTemplateMock.exchange(properties.getUrl(), HttpMethod.POST, new HttpEntity<>(refundsReportRequest, headers), RefundReportResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
        expectedException.expect(HttpClientErrorException.class);
        expectedException.expectMessage(expectedExceptionMessage);

        try {
            reportService.getRefundsReport(refundsReportRequest, authToken);
            fail("HttpClientErrorException must be thrown");
        } catch (Exception exp) {
            // THEN
            verify(restTemplateMock, times(1)).exchange(properties.getUrl(), HttpMethod.POST, new HttpEntity<>(refundsReportRequest, headers), RefundReportResponse.class);
            assertThat("Fault [expected 'Exception Message' asserts]",
                    exp.getMessage(),
                    is(expectedExceptionMessage));
            throw exp;
        }
    }

    @Test
    public void getRefundsReportWithInvalidRefundsReportRequestAndValidAuthorizationTokenShouldThrowInternalServerErrorException() throws Exception {
        // GIVEN
        final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJhZG1pbiIsIm1lcmNoYW50SWQiOjMsInN1Yk1lcmNoYW50SWRzIjpbMyw3NCw5MywxMTkxLDExMSwxMzcsMTM4LDE0MiwxNDUsMTQ2LDE1MywzMzQsMTc1LDE4NCwyMjAsMjIxLDIyMiwyMjMsMjk0LDMyMiwzMjMsMzI3LDMyOSwzMzAsMzQ5LDM5MCwzOTEsNDU1LDQ1Niw0NzksNDg4LDU2MywxMTQ5LDU3MCwxMTM4LDExNTYsMTE1NywxMTU4LDExNzldLCJ0aW1lc3RhbXAiOjE1MDQxMDg3NzN9.Jt5JVXoEEkck4M9fbmDOaykhMpoq-x-D40rY-7Hv_fQ";
        final String expectedExceptionMessage = "500 INTERNAL_SERVER_ERROR";

        HttpHeaders headers = TestUtils.generateAuthorizationHeader(authToken);

        RefundsReportRequest refundsReportRequest = new RefundsReportRequest(); 
		
        refundsReportRequest.setFromDate(Calendar.getInstance().getTime());
        refundsReportRequest.setToDate(Calendar.getInstance().getTime());
        refundsReportRequest.setMerchant(2);
        refundsReportRequest.setAcquirer(5);
        
        // WHEN
        when(restTemplateMock.exchange(properties.getUrl(), HttpMethod.POST, new HttpEntity<>(refundsReportRequest, headers), RefundReportResponse.class))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        expectedException.expect(HttpServerErrorException.class);
        expectedException.expectMessage(expectedExceptionMessage);

        try {
            reportService.getRefundsReport(refundsReportRequest, authToken);
            fail("HttpServerErrorException must be thrown");
        } catch (Exception exp) {
            // THEN
            verify(restTemplateMock, times(1)).exchange(properties.getUrl(), HttpMethod.POST, new HttpEntity<>(refundsReportRequest, headers), RefundReportResponse.class);
            assertThat("Fault [expected 'Exception Message' asserts]",
                    exp.getMessage(),
                    is(expectedExceptionMessage));
            throw exp;
        }
    }

}
