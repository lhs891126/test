package kr.co.zlgoon.scheduler.service.job;

import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

	
	public void job1() throws InterruptedException {
		System.out.println("스케쥴 1");
		Thread.sleep(2000);
	}
	
	
	public void job2() throws InterruptedException {
		System.out.println("스케쥴2");
		Thread.sleep(4000);
	}
	
}
