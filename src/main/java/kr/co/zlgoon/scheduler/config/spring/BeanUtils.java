package kr.co.zlgoon.scheduler.config.spring;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import kr.co.zlgoon.scheduler.annotation.AddScheduler;

/**
 * Spring Bean 가져오기
 * 
 * @author hosuk
 *
 */
public class BeanUtils {
	
	public static Object getBean(Class<?> classType) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		return applicationContext.getBean(classType);
	}
	
	public static Map<String, Object> getBeanByAnnotaion(Class<? extends Annotation> annotation) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		return applicationContext.getBeansWithAnnotation(annotation);
	}

}
