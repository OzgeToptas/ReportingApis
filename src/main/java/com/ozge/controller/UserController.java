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
import com.ozge.model.error.LoginError;
import com.ozge.model.request.Credentials;
import com.ozge.model.request.MerchantUserRequest;
import com.ozge.service.UserService;

@RestController
public class UserController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Login User
	 * @param credentials
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/user/login", produces = "application/json; charset=UTF-8")
	public ResponseEntity userLogin(@RequestBody @Valid Credentials credentials, BindingResult bindingResult) {

		log.info("User login attempt -> Credential ( email : {} - password : {} )", credentials.getEmail(),
				credentials.getPassword());

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return userService.login(credentials).map(authToken -> new ResponseEntity<>(authToken, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(ErrorResponse.create(new LoginError()),
						HttpStatus.INTERNAL_SERVER_ERROR));

	}

	/**
	 * Get Merchant user info.
	 * @param authToken
	 * @param merchantUserRequest
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/user/show", produces = "application/json; charset=UTF-8")
	public ResponseEntity getMerchantUserInformation(
			@RequestHeader(value = "Authorization", required = false) String authToken,
			@RequestBody @Valid MerchantUserRequest merchantUserRequest, BindingResult bindingResult) {

		log.info("Merchant user information request attempt -> ( User id : {} ) - Authorization ( {} )",
				merchantUserRequest.getId(), authToken);

		if (authToken.isEmpty()) {
			return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Missed!")),
					HttpStatus.UNAUTHORIZED);
		}

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return userService.getMerchantUserInformation(merchantUserRequest, authToken)
				.map(merchantUserInfoResponse -> new ResponseEntity<>(merchantUserInfoResponse, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR));

	}

}
