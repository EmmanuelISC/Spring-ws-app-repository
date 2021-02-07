package com.emmanuel.app.ws.security;

import com.emmanuel.app.ws.SpringApplicationContext;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 112400000;
	public static final String TOKEN_PREFIX = "Berer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
	
	public static String getTokenSecret()
	{
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}
}
