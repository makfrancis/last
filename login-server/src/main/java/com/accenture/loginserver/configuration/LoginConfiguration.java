package com.accenture.loginserver.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.accenture.loginserver.service.AccountCrudService;
import com.accenture.loginserver.service.LoginService;

@Configuration
public class LoginConfiguration extends ResourceConfig{

	public LoginConfiguration() {
		register(AccountCrudService.class);
		register(LoginService.class);
	}

}
