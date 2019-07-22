package com.ozge.service;

import java.util.Optional;

import com.ozge.exception.NullCustomerInfoException;
import com.ozge.model.request.ClientRequest;
import com.ozge.model.response.ClientResponse;

public interface ClientService {

	Optional<ClientResponse> getClientInformation(ClientRequest clientRequest, String authToken)
			throws NullCustomerInfoException;

}
