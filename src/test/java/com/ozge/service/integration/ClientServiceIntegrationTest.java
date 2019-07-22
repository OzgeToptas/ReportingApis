package com.ozge.service.integration;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ozge.configuration.properties.UserServiceProperties;
import com.ozge.exception.NullCustomerInfoException;
import com.ozge.model.request.ClientRequest;
import com.ozge.model.response.ClientResponse;
import com.ozge.service.ClientService;
import com.ozge.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private ClientService clientService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserServiceProperties properties;

    private final String email = "demo@bumin.com.tr";
    private final String password = "cjaiU8CV";


    @Test
    public void getClientInformationWithValidTransactionIdAndAuthorizationTokenShouldReturnClientResponse() throws Exception {
        final String authToken = TestUtils.generateAuthorizationTokenWithValidCredentials(restTemplate, properties.getLogin().getUrl(), email, password);
        final String transactionId = "982786-1503662147-3";

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setTransactionId(transactionId);

        Optional<ClientResponse> optional = clientService.getClientInformation(clientRequest, authToken);

        assertTrue("Fault [expected true]", optional.isPresent());
        assertNotNull("Fault [expected 'Customer Path Id' not null]",
                optional.get().getCustomerInfo().getId());
        assertNotNull("Fault [expected 'Customer Path Email' not null]",
                optional.get().getCustomerInfo().getEmail());
        assertNotNull("Fault [expected 'Customer Path Billing City' not null]",
                optional.get().getCustomerInfo().getBillingCity());
    }

    @Test
    public void getClientInformationWithInvalidAuthorizationTokenShouldThrowUnauthorizedException() throws Exception {
        // GIVEN
        final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudFVzZXJJZCI6NTMsInJvbGUiOiJhZG1pbiIsIm1lcmNoYW50SWQiOjMsInN1Yk1lcmNoYW50SWRzIjpbMyw3NCw5MywxMTkxLDExMSwxMzcsMTM4LDE0MiwxNDUsMTQ2LDE1MywzMzQsMTc1LDE4NCwyMjAsMjIxLDIyMiwyMjMsMjk0LDMyMiwzMjMsMzI3LDMyOSwzMzAsMzQ5LDM5MCwzOTEsNDU1LDQ1Niw0NzksNDg4LDU2MywxMTQ5LDU3MCwxMTM4LDExNTYsMTE1NywxMTU4LDExNzldLCJ0aW1lc3RhbXAiOjE1MDQxMDg3NzN9.Jt5JVXoEEkck4M9fbmDOaykhMpoq-x-D40rY-7Hv_fQ";
        final String transactionId = "982786-1503662147-3";
        final String expectedExceptionMessage = "401 Unauthorized";

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setTransactionId(transactionId);

        expectedException.expect(HttpClientErrorException.class);
        expectedException.expectMessage(expectedExceptionMessage);

        try {
            clientService.getClientInformation(clientRequest, authToken);
            fail("HttpClientErrorException must be thrown");
        } catch (Exception exp) {
            assertThat("Fault [expected 'Exception Message' asserts]",
                    exp.getMessage(),
                    is(expectedExceptionMessage));
            throw exp;
        }
    }

    @Test
    public void getClientInformationWithVoidTransactionIdAndValidAuthorizationTokenShouldThrowNullCustomerInfoException() throws Exception {
        final String authToken = TestUtils.generateAuthorizationTokenWithValidCredentials(restTemplate, properties.getLogin().getUrl(), email, password);
        final String transactionId = "1-1444392550-1";
        final String expectedExceptionMessage = "Customer Information cannot be null";

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setTransactionId(transactionId);

        expectedException.expect(NullCustomerInfoException.class);
        expectedException.expectMessage(expectedExceptionMessage);

        try {
            clientService.getClientInformation(clientRequest, authToken);
            fail("NullCustomerInfoException must be thrown");
        } catch (Exception exp) {
            assertThat("Fault [expected 'Exception Message' asserts]",
                    exp.getMessage(),
                    is(expectedExceptionMessage));
            throw exp;
        }
    }

    @Test
    public void getClientInformationWithNotReadableHttpMessageShouldThrowHttpMessageNotReadableException() throws Exception {
        final String authToken = TestUtils.generateAuthorizationTokenWithValidCredentials(restTemplate, properties.getLogin().getUrl(), email, password);
        final String transactionId = "1";

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setTransactionId(transactionId);

        expectedException.expect(HttpMessageNotReadableException.class);

        try {
            clientService.getClientInformation(clientRequest, authToken);
            fail("HttpMessageNotReadableException must be thrown");
        } catch (Exception exp) {
            throw exp;
        }
    }

}
