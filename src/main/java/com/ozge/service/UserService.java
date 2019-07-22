package com.ozge.service;


import java.util.Optional;

import com.ozge.model.request.Credentials;
import com.ozge.model.request.MerchantUserRequest;
import com.ozge.model.response.AuthToken;
import com.ozge.model.response.MerchantUserInfoResponse;

public interface UserService {

    Optional<AuthToken> login(Credentials credentials);

    Optional<MerchantUserInfoResponse> getMerchantUserInformation(MerchantUserRequest merchantUserRequest, String authToken);

}
