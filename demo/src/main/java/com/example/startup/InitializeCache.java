package com.example.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitializeCache {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		logger.info(" Test context refreshed event");
	}

}
