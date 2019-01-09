package com.accenture.bars.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.accenture.bars.ping.BarsPingService;
import com.accenture.bars.service.BarsService;

@Configuration
public class BarsConfiguration extends ResourceConfig{

	public BarsConfiguration() {
		register(BarsService.class);
		register(BarsPingService.class);
	}

}
