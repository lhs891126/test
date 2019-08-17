package kr.co.zlgoon.scheduler.server;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import kr.co.zlgoon.scheduler.annotation.AddScheduler;
import kr.co.zlgoon.scheduler.vo.SchedulerVo;

/**
 * zlgoon scheduler manager
 * 
 * @author hosuk
 *
 */
public class SchedulerManager {
	
	public final static String TRIGGER_TYPE_C 			= "C";		//트리거타입 - CRON
	public final static String TRIGGER_TYPE_R 			= "R";		//트리거타입 - FixedRate
	public final static String TRIGGER_TYPE_D 			= "D";		//트리거타입 - FixedDelay
	
	public final static String RUNNING_STATUS_I 		= "I";		//잡 프로세스 - 진행중
	public final static String RUNNING_STATUS_S 		= "S";		//잡 프로세스- 중지
	
	public final static String JOB_STATUS_I 			= "I";		//잡 실행상태 - 진행중
	public final static String JOB_STATUS_S 			= "S";		//잡 실행상태- 중지
	
	
	private static List<SchedulerVo> schedulerProvider;
	
	public static void setSchedulerBeans(List<SchedulerVo> obj) {
		schedulerProvider = obj;
	}
	
	public static List<SchedulerVo> getSchedulerBeans() {
		return schedulerProvider;
	}
	
	public static SchedulerVo getSchedulerBeansByName(String schedulerName) {
		SchedulerVo obj = null;
		
		for(SchedulerVo item : schedulerProvider) {
			if(item.getSchedulerName().equals(schedulerName)) {
				obj = item;
			}
		}
		
		return obj;
	}
	
	public static String getSchedulerName(Object obj) {
		AddScheduler annotaion = obj.getClass().getAnnotation(AddScheduler.class);
		return annotaion.name();
	}
	
	public static Trigger getTrigger(String triggerType, String triggeValue) throws Exception {
		
		try {
			if(TRIGGER_TYPE_C.equals(triggerType)) {
				return new CronTrigger(triggeValue);
			} if(TRIGGER_TYPE_D.equals(triggerType) || TRIGGER_TYPE_R.equals(triggerType)) {
				PeriodicTrigger periodicTrigger = new PeriodicTrigger(Long.parseLong(triggeValue), TimeUnit.SECONDS);
				
				if(TRIGGER_TYPE_R.equals(triggerType)) {
					periodicTrigger.setFixedRate(true);
				}
				
				return periodicTrigger;
				
			} else {
				throw new Exception("Undefined triggertype triggerType : " + triggerType);
			}
			
		} catch (Exception e) {
			throw new Exception("[getTrigger] " + e, e);
		}

	}
}
