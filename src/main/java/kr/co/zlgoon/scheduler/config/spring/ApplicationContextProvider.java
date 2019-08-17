package kr.co.zlgoon.scheduler.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		applicationContext = ctx;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
