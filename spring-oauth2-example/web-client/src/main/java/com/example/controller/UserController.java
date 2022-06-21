package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.example.response.UserResponse;

@Controller
public class UserController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OAuth2AuthorizedClientService outh2AuthorizedClientService;

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("/users")
	public String getUsers(Model model, @AuthenticationPrincipal OidcUser principal) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

		OAuth2AuthorizedClient oauth2Client =
				this.outh2AuthorizedClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(),
																		oauthToken.getName());

		String jwtAccessToken = oauth2Client.getAccessToken().getTokenValue();
		
		OidcIdToken idToken = principal.getIdToken();
		String idTokenValue = idToken.getTokenValue();
		System.out.println("idTokenValue  : " + idTokenValue);
		System.out.println("jwtAccessToken: " + jwtAccessToken);
		
		String url = "http://localhost:8082/users";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwtAccessToken);
		HttpEntity<List<UserResponse>> entity = new HttpEntity<>(headers);

		ResponseEntity<List<UserResponse>> responseEntity =
				this.restTemplate.exchange(url,
											HttpMethod.GET,
											entity,
											new ParameterizedTypeReference<List<UserResponse>>(){});
		
		List<UserResponse> users = responseEntity.getBody();
	
		model.addAttribute("users", users);

		return "users";
	}
}
