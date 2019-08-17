package kr.co.zlgoon.scheduler.server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.StopWatch;

import kr.co.zlgoon.scheduler.service.scheduler.SchedulerManagerService;

/**
 * 스케쥴러
 * 
 * @author hosuk
 *
 */
public abstract class ZlgoonAbstractSceduler {
	
	Logger log = LoggerFactory.getLogger(ZlgoonAbstractSceduler.class);
	
	private ThreadPoolTaskScheduler scheduler;
	private ScheduledFuture<?> schedulerFuture;
	private String schedulerName = "";
	
	@Autowired
	SchedulerManagerService schedulerManagerService;
	

	/**
	 * 진행 상태 확인 (true : 실행중, false : 종료)
	 * 
	 * @return
	 */
	public boolean isRunning() {
		
		if(scheduler == null || schedulerFuture == null) {
			return false;
		}
		
		if(schedulerFuture.isCancelled()) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * 스케쥴 stop 
	 */
	public void stop() {
		schedulerFuture.cancel(false);
		scheduler.shutdown();
		
		schedulerManagerService.updateRunningStatusBySchedulerName(schedulerName, SchedulerManager.JOB_STATUS_S);
	}
	
	
	/**
	 * 스케쥴 stop (강제종료) 
	 * 호출시 바로 종료
	 */
	public void stopNow() {
		schedulerFuture.cancel(true);
		scheduler.shutdown();
		
		schedulerManagerService.updateRunningStatusBySchedulerName(schedulerName, SchedulerManager.JOB_STATUS_S);
	}
	
	
	/**
	 * 스케쥴러 Start
	 * 
	 * @param schedulerName			[스케쥴명] 쓰레드명으로도 사용
	 * @param poolSize				[풀 사이즈]
	 * @param trigger				[트리거]
	 */
	public void start(String schedulerName, int poolSize, Trigger trigger) throws Exception {
		scheduler = new ThreadPoolTaskScheduler();
		
		scheduler.setPoolSize(poolSize);
		scheduler.setThreadNamePrefix(schedulerName);  
		scheduler.setWaitForTasksToCompleteOnShutdown(true);   
		scheduler.setAwaitTerminationSeconds(10);
		
		scheduler.initialize();
		
		this.schedulerName = schedulerName;
		
		schedulerFuture = scheduler.schedule(getRunnable(), trigger);
		
		schedulerManagerService.updateRunningStatusBySchedulerName(schedulerName, SchedulerManager.JOB_STATUS_I);
		
	}
	
	
	/**
	 * 스케쥴 run
	 * 
	 * @return
	 */
	private Runnable getRunnable() {
		return new Runnable() {
			
			@Override
			public void run() {
				LocalDateTime currentDate = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
				StopWatch stopWatch = new StopWatch();
				String errorYn = "N";
				
				schedulerManagerService.updateRunningInfoBySchedulerName(schedulerName, 0, SchedulerManager.RUNNING_STATUS_I, currentDate.format(formatter));
				stopWatch.start();
				
				try {
					before();
					runner();
					after();
				} catch (Exception e) {
					errorYn = "Y";
				} finally {
					stopWatch.stop();
					log.info("running time(ms) : " + stopWatch.getTotalTimeMillis());
				}
				
				schedulerManagerService.updateRunningInfoBySchedulerName(schedulerName, stopWatch.getTotalTimeMillis(), SchedulerManager.RUNNING_STATUS_S, "");
				
			}
		};
	}
	
	
	
	/**
	 * 스케쥴 runner
	 * 
	 */
	public abstract void runner() throws Exception;
	
	/**
	 * 스케쥴  before 작업
	 * 
	 */
	public abstract void before() throws Exception;
	
	/**
	 * 스케쥴 after 작업
	 * 
	 */
	public abstract void after() throws Exception;
	
}
