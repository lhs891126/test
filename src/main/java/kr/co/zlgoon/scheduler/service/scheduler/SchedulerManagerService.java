package kr.co.zlgoon.scheduler.service.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Service;

import kr.co.zlgoon.scheduler.job.Scheduler1;
import kr.co.zlgoon.scheduler.repository.SchedulerRepository;
import kr.co.zlgoon.scheduler.server.SchedulerManager;
import kr.co.zlgoon.scheduler.server.ZlgoonAbstractSceduler;
import kr.co.zlgoon.scheduler.vo.ResultListVo;
import kr.co.zlgoon.scheduler.vo.ResultVo;
import kr.co.zlgoon.scheduler.vo.SchedulerVo;

@Service
public class SchedulerManagerService {
	
	Logger log = LoggerFactory.getLogger(SchedulerManagerService.class);
	
	@Autowired
	SchedulerRepository schedulerRepository;

	
	/**
	 * Scheduler STOP
	 */
	public ResultListVo stopScheduler(String schedulerName) throws Exception {
		ResultListVo 				resultVo 			= new ResultListVo();
		List<Map<String, String>> 	failSchedulerList 	= new ArrayList<Map<String, String>>();
		
		if("all".equals(schedulerName)) {
			List<SchedulerVo> schedulerList = SchedulerManager.getSchedulerBeans();
			
			if(schedulerList == null) {
				throw new Exception("The scheduler bean is not registered");
			}
			
			for(SchedulerVo item : schedulerList) {
				try {
					ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) item.getSchedulerBean();
					
					if(!scheduler.isRunning()) {
						log.info("The schedule has already ended schedulerName : " + schedulerName);
					} else {
						scheduler.stop();
						log.info("[schedule stop] schedulerName : " + item.getSchedulerName());
					}
					
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					
					Map<String, String> fail = new HashMap<String, String>();
					fail.put(item.getSchedulerName(), e.getMessage());
					
					failSchedulerList.add(fail);
				}
			}
		} else {
			SchedulerVo schedulerVo = SchedulerManager.getSchedulerBeansByName(schedulerName);
			
			if(schedulerVo == null) {
				throw new Exception("존재하지 않는 스케쥴러명 입니다. schedulerName : " + schedulerName);
			}
			
			ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) schedulerVo.getSchedulerBean();
			
			try {
				
				if(!scheduler.isRunning()) {
					throw new Exception("The schedule has already ended");
				}
				
				scheduler.stop();
				log.info("[schedule stop] schedulerName : " + schedulerVo.getSchedulerName());
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				
				Map<String, String> fail = new HashMap<String, String>();
				fail.put(schedulerName, e.getMessage());
				
				failSchedulerList.add(fail);
			}
			
		}
		
		resultVo.setResultCode("0000");
		resultVo.setResultMsg("SUCCESS");
		resultVo.setFailList(failSchedulerList);
		
		return resultVo;
	}
	
	
	/**
	 * 스케쥴 강제종료 (호출 즉시 바로 종료)
	 * 
	 * @param schedulerName
	 * @return ResultListVo
	 * @throws Exception
	 */
	public ResultListVo stopNowScheduler(String schedulerName) throws Exception {
		ResultListVo 				resultVo 			= new ResultListVo();
		List<Map<String, String>> 	failSchedulerList 	= new ArrayList<Map<String, String>>();
		
		if("all".equals(schedulerName)) {
			List<SchedulerVo> schedulerList = SchedulerManager.getSchedulerBeans();
			
			if(schedulerList == null) {
				throw new Exception("The scheduler bean is not registered");
			}
			
			for(SchedulerVo item : schedulerList) {
				try {
					ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) item.getSchedulerBean();
					
					if(!scheduler.isRunning()) {
						log.info("The schedule has already ended schedulerName : " + schedulerName);
					} else {
						scheduler.stopNow();
						log.info("[schedule stop] schedulerName : " + item.getSchedulerName());
					}
					
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					
					Map<String, String> fail = new HashMap<String, String>();
					fail.put(item.getSchedulerName(), e.getMessage());
					
					failSchedulerList.add(fail);
				}
			}
		} else {
			SchedulerVo schedulerVo = SchedulerManager.getSchedulerBeansByName(schedulerName);
			
			if(schedulerVo == null) {
				throw new Exception("존재하지 않는 스케쥴러명 입니다. schedulerName : " + schedulerName);
			}
			
			ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) schedulerVo.getSchedulerBean();
			
			try {
				
				if(!scheduler.isRunning()) {
					throw new Exception("The schedule has already ended");
				}
				
				scheduler.stopNow();
				log.info("[schedule stop] schedulerName : " + schedulerVo.getSchedulerName());
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				
				Map<String, String> fail = new HashMap<String, String>();
				fail.put(schedulerName, e.getMessage());
				
				failSchedulerList.add(fail);
			}
			
		}
		
		resultVo.setResultCode("0000");
		resultVo.setResultMsg("SUCCESS");
		resultVo.setFailList(failSchedulerList);
		
		return resultVo;
	}
	
	
	/**
	 * Scheduler START
	 */
	public ResultListVo startScheduler(String schedulerName) throws Exception {
		ResultListVo 				resultVo 			= new ResultListVo();
		List<Map<String, String>> 	failSchedulerList 	= new ArrayList<Map<String, String>>();
		
		if("all".equals(schedulerName)) {
			List<SchedulerVo> schedulerList = SchedulerManager.getSchedulerBeans();
			
			if(schedulerList == null) {
				throw new Exception("The scheduler bean is not registered");
			}
			
			for(SchedulerVo item : schedulerList) {
				try {
					
					ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) item.getSchedulerBean();
					
					if(scheduler.isRunning()) {
						throw new Exception("The schedule is already running schedulerName : " + item.getSchedulerName());
					} 
					
					scheduler.start(item.getSchedulerName(), item.getPoolSize(), SchedulerManager.getTrigger(item.getTriggerType(), item.getTriggerValue()));
					
					log.info("[schedule start] schedulerName : " + item.getSchedulerName());
				
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					
					Map<String, String> fail = new HashMap<String, String>();
					fail.put(item.getSchedulerName(), e.getMessage());
					
					failSchedulerList.add(fail);
				}
			}
			
		} else {
			SchedulerVo schedulerVo = SchedulerManager.getSchedulerBeansByName(schedulerName);
			
			if(schedulerVo == null) {
				throw new Exception("The schedule name does not exist schedulerName : " + schedulerName);
			}
			
			ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) schedulerVo.getSchedulerBean();
			
			try {
				if(scheduler.isRunning()) {
					throw new Exception("The schedule is already running");
				}
				
				scheduler.start(schedulerVo.getSchedulerName(), schedulerVo.getPoolSize(), SchedulerManager.getTrigger(schedulerVo.getTriggerType(), schedulerVo.getTriggerValue()));
				
				log.info("[schedule start] schedulerName : " + schedulerVo.getSchedulerName());
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				
				Map<String, String> fail = new HashMap<String, String>();
				fail.put(schedulerName, e.getMessage());
				
				failSchedulerList.add(fail);
			}
		}
		
		resultVo.setResultCode("0000");
		resultVo.setResultMsg("SUCCESS");
		resultVo.setFailList(failSchedulerList);
		
		return resultVo;
	}
	
	
	/**
	 * 스케쥴 단건 실행
	 * 
	 * @param schedulerName
	 * @throws Exception 
	 */
	public void executeScheduler(String schedulerName) throws Exception {
		SchedulerVo 	schedulerVo 	= SchedulerManager.getSchedulerBeansByName(schedulerName);
		
		if(schedulerVo == null) {
			throw new Exception("The schedule name does not exist schedulerName : " + schedulerName);
		}
		
		ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) schedulerVo.getSchedulerBean();
		
		scheduler.before();
		scheduler.runner();
		scheduler.after();
	}
	
	
	/**
	 * 스케쥴려 상태 확인
	 * 
	 * @param schedulerName
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getSchedulerStatus(String schedulerName) throws Exception {
		SchedulerVo schedulerVo = SchedulerManager.getSchedulerBeansByName(schedulerName);
		
		if(schedulerVo == null) {
			throw new Exception("The schedule name does not exist schedulerName : " + schedulerName);
		}
		
		ZlgoonAbstractSceduler scheduler = (ZlgoonAbstractSceduler) schedulerVo.getSchedulerBean();
		
		boolean test = scheduler.isRunning();
		
		System.out.println(test);
		
		return new HashMap<>();
	}
	
	
	/**
	 * 스케쥴 상태 업데이트 (결과 무시)
	 * 
	 * @param schedulerName
	 * @param runningStatus
	 */
	public void updateRunningStatusBySchedulerName(String schedulerName, String jobStatus) {
		try {
			SchedulerVo schedulerVo = new SchedulerVo();
			
			schedulerVo.setSchedulerName(schedulerName);
			schedulerVo.setJobStatus(jobStatus);
			schedulerRepository.updateRunningStatusBySchedulerName(schedulerVo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 스케쥴 정보 업데이트 (결과무시)
	 * 
	 * @param schedulerName
	 * @param runningTime
	 */
	public void updateRunningInfoBySchedulerName(String schedulerName, long runningTime, String runningStatus, String lastExeDate) {
		try {
			SchedulerVo schedulerVo = new SchedulerVo();
			
			schedulerVo.setSchedulerName(schedulerName);
			schedulerVo.setRunningTime(runningTime);
			schedulerVo.setRunningStatus(runningStatus);
			schedulerVo.setLastExeDate(lastExeDate);
			schedulerRepository.updateRunningInfoBySchedulerName(schedulerVo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
}
