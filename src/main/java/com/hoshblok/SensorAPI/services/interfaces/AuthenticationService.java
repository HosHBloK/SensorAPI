package com.hoshblok.SensorAPI.services.interfaces;

import javax.servlet.http.Cookie;

import com.hoshblok.SensorAPI.dto.auth.request.LoginRequest;
import com.hoshblok.SensorAPI.dto.auth.response.AuthResponse;

public interface AuthenticationService {

	Cookie getRefreshTokenCookieForLogin(LoginRequest request);

	Cookie getRefreshTokenCookieForRefresh(String cookieWithToken);

	AuthResponse getAuthResponseForLogin(LoginRequest request);

	AuthResponse getAuthResponseForRefresh(String refreshToken);
}