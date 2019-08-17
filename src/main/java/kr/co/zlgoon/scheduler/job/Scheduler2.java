package kr.co.zlgoon.scheduler.job;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.zlgoon.scheduler.annotation.AddScheduler;
import kr.co.zlgoon.scheduler.server.ZlgoonAbstractSceduler;
import kr.co.zlgoon.scheduler.service.job.SchedulerService;

@AddScheduler(name = "scheduler2_name")
public class Scheduler2 extends ZlgoonAbstractSceduler {
	
	@Autowired
	SchedulerService schedulerService;

	@Override
	public void runner() throws Exception {
		schedulerService.job2();
	}

	@Override
	public void before() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void after() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
