package kr.co.zlgoon.scheduler.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class EtcTest {

	@Test
	public void localDateTimeTest() {
		try {
			LocalDateTime localDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			
			System.out.println(localDateTime);
			System.out.println(localDateTime.format(formatter));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
