package kr.co.zlgoon.scheduler.job;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.zlgoon.scheduler.annotation.AddScheduler;
import kr.co.zlgoon.scheduler.server.ZlgoonAbstractSceduler;
import kr.co.zlgoon.scheduler.service.job.SchedulerService;

@AddScheduler(name = "scheduler1_name")
public class Scheduler1 extends ZlgoonAbstractSceduler {

	@Autowired
	SchedulerService schedulerService;
	
	@Override
	public void runner() throws Exception {
		schedulerService.job1();
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
