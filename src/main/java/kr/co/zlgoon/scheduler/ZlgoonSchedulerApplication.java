package kr.co.zlgoon.scheduler;


import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import kr.co.zlgoon.scheduler.server.SchedulerSetting;


@SpringBootApplication
@EnableScheduling
public class ZlgoonSchedulerApplication {
	
	Logger log = LoggerFactory.getLogger(ZlgoonSchedulerApplication.class);
	
	@Autowired
	SchedulerSetting schedulerSetting;

	public static void main(String[] args) {
		SpringApplication.run(ZlgoonSchedulerApplication.class, args);
	}
	
	/**
	 * ApplicationReadyEvent Listener
	 * 
	 * 프로그램 시작시 초기화 처리
	 * 
	 * @throws Exception
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void init () throws Exception {
		log.info(" ================== Zlgoon  Schedeuler Start ================== ");
		
		schedulerSetting.init();
		log.info(" >> Schedeuler Init OK  ");
		
		schedulerSetting.startAllJob();
		log.info(" >> Schedeuler startAllJob OK  ");
	}

	
	/**
	 * PreDestory 
	 * 
	 * 프로그램 종료시 처리
	 * 
	 * @throws Exception
	 */
	@PreDestroy
	public void exit() throws Exception {
		
		schedulerSetting.stopAllJob();
		log.info(" >> Schedeuler stopAllJob OK  ");
		
		log.info(" ================== Zlgoon  Schedeuler END ================== ");
	}
	
}
