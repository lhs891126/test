package kr.co.zlgoon.scheduler.server;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.zlgoon.scheduler.annotation.AddScheduler;
import kr.co.zlgoon.scheduler.config.spring.BeanUtils;
import kr.co.zlgoon.scheduler.repository.SchedulerRepository;
import kr.co.zlgoon.scheduler.service.scheduler.SchedulerManagerService;
import kr.co.zlgoon.scheduler.vo.ResultListVo;
import kr.co.zlgoon.scheduler.vo.SchedulerVo;

@Component
public class SchedulerSetting {
	
	Logger log = LoggerFactory.getLogger(SchedulerSetting.class);
	
	@Autowired
	SchedulerRepository schedulerRepository;
	
	@Autowired
	SchedulerManagerService schedulerManagerService;
	
	
	/**
	 * 모든 스케쥴러 start
	 * 
	 */
	public void startAllJob() throws Exception {
		try {
			ResultListVo resultListVo =  schedulerManagerService.startScheduler("all");
			
			List<Map<String, String>> failList = resultListVo.getFailList();
			
		} catch (Exception e) {
			throw new Exception("[Schduler startAllJob] " + e);
		}
	}
	

	/**
	 * 스케쥴 모듈 시작 시 초기화
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		
		try {
			List<SchedulerVo> list = schedulerRepository.getZlgoonSchedulerList();
			Map<String, Object> schedulerBeans = BeanUtils.getBeanByAnnotaion(AddScheduler.class);
		
			if(list.size() == 0) {
				throw new Exception("The scheduler is not registered in db");
			}
			
			if(schedulerBeans == null) {
				throw new Exception("There are no registered schedulers");
			}
			
			if(list.size() != schedulerBeans.size()) {
				throw new Exception("The number stored in the db differs from the number registered in the scheduler");
			}
			
			for(SchedulerVo item : list) {
				boolean isCheck = false;
				String schedulerNameByDb = item.getSchedulerName();
				Iterator it = schedulerBeans.keySet().iterator();
				
				while(it.hasNext()) {
					String key = (String) it.next();
					AddScheduler annotaion = schedulerBeans.get(key).getClass().getAnnotation(AddScheduler.class);
					
					if(schedulerNameByDb.equals(annotaion.name())) {
						isCheck = true;
						item.setSchedulerBean(schedulerBeans.get(key));
					}
				}
				
				if(!isCheck) {
					throw new Exception("There is no registered scheduler name in db  schedulerName : " + schedulerNameByDb);
				}
				
			}
			
			log.info(" ======== Scheduler Init ======== ");
			for(SchedulerVo i : list) {
				log.info("name : " + i.getSchedulerName() + " / triggerType : " + i.getTriggerType() + " / triggerValue : " + i.getTriggerValue());
			}
			log.info(" ======== Scheduler Init ======== ");
			
			SchedulerManager.setSchedulerBeans(list);
 			
		} catch (Exception e) {
			throw new Exception("[Schduler init] " + e);
		}
	}
	
	
	/**
	 * 스케쥴 종료시 
	 * @throws Exception 
	 * 
	 */
	public void stopAllJob() throws Exception {
		try {
			schedulerManagerService.stopScheduler("all");
		} catch (Exception e) {
			throw new Exception("[Schduler stopAllJob] " + e);
		}
	}
}

