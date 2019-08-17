package kr.co.zlgoon.scheduler.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.zlgoon.scheduler.annotation.AddScheduler;
import kr.co.zlgoon.scheduler.config.spring.BeanUtils;
import kr.co.zlgoon.scheduler.repository.SchedulerRepository;
import kr.co.zlgoon.scheduler.server.SchedulerManager;
import kr.co.zlgoon.scheduler.server.ZlgoonAbstractSceduler;
import kr.co.zlgoon.scheduler.vo.SchedulerVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
	
	Logger log = LoggerFactory.getLogger(ServiceTest.class);
	
	@Autowired
	SchedulerRepository schedulerRepository;
	
	
	@Ignore
	public void getSchedulerListTest() throws Exception {
		try {
			List<SchedulerVo> list = schedulerRepository.getZlgoonSchedulerList();
			
			for(SchedulerVo item : list) {
				System.out.println(item.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Ignore
	public void getBeanByAnnotaionTest() {
		Map<String, Object> test = BeanUtils.getBeanByAnnotaion(AddScheduler.class);
		test.forEach((key, value) -> System.out.println(key + ":" + value));
	}
	
	
	@Ignore
	public void annotaionTest () {

	}
	
}
