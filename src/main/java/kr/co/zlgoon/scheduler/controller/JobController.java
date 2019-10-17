package kr.co.zlgoon.scheduler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.zlgoon.scheduler.service.scheduler.SchedulerManagerService;
import kr.co.zlgoon.scheduler.vo.ResultListVo;
import kr.co.zlgoon.scheduler.vo.ResultVo;

@RestController
public class JobController {
	
	Logger log = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	SchedulerManagerService schedulerManagerService;
	
	
	/**
	 * 스케쥴 종료
	 * 
	 * @return
	 */
	@GetMapping("/stop/{name}")
	public ResultListVo stop(@PathVariable("name") String schedulerName) {
		ResultListVo result = null;
		
		try {
			result = schedulerManagerService.stopScheduler(schedulerName);
			log.debug("Aaaaaaaaaaaaaaaaa");
			log.debug("Aaaaaaaaaaaaaaaaa");
		} catch (Exception e) {
			log.error(e.getMessage());
			
			result = new ResultListVo();
			result.setResultCode("ERR99");
			result.setResultMsg(e.getMessage());
		}
		
		return result;
	}
	
	
	/**
	 * 스케쥴 강제 종료
	 * 
	 * @return
	 */
	@GetMapping("/stopNow/{name}")
	public ResultListVo stopNow(@PathVariable("name") String schedulerName) {
		ResultListVo result = null;
		
		try {
			result = schedulerManagerService.stopNowScheduler(schedulerName);
		} catch (Exception e) {
			log.error(e.getMessage());
			
			result = new ResultListVo();
			result.setResultCode("ERR99");
			result.setResultMsg(e.getMessage());
		}
		
		return result;
	}
	
	
	/**
	 * 스케쥴 시작
	 * 
	 * @return
	 */
	@GetMapping("/start/{name}")
	public ResultListVo start(@PathVariable("name") String schedulerName) {
		ResultListVo result = null;
		
		try {
			result = schedulerManagerService.startScheduler(schedulerName);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			result = new ResultListVo();
			result.setResultCode("ERR99");
			result.setResultMsg(e.getMessage());
		}
		
		return result;
	}

	
	
	/**
	 * 스케쥴 한번 실행
	 * 
	 * @param schedulerName
	 * @return String
	 */
	@GetMapping("/execute/{name}")
	public ResultVo execute(@PathVariable("name") String schedulerName) {
		ResultVo result = new ResultVo();
		
		try {
			schedulerManagerService.executeScheduler(schedulerName);
			
			result.setResultCode("0000");
			result.setResultMsg("SUCCESS");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			result.setResultCode("ERR99");
			result.setResultMsg(e.getMessage());
		}
		
		return result;
	}
	
	
	
	/**
	 * 스케쥴 상태 확인
	 * 
	 * @param schedulerName
	 * @return
	 */
	@GetMapping("/status/{name}")
	public ResultVo getSchedulerStatus(@PathVariable("name") String schedulerName) {
		ResultVo result = new ResultVo();
		
		log.info("=========== [getSchedulerStatus Start] ===========");
		log.info("name : " + schedulerName);
		
		try {
			schedulerManagerService.getSchedulerStatus(schedulerName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			result.setResultCode("ERR99");
			result.setResultMsg(e.getMessage());
		}
		
		log.info("=========== [getSchedulerStatus End] ===========");
		
		return result; 
	}
	
	
}
