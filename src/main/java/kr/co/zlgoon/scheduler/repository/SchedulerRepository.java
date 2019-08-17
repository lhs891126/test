package kr.co.zlgoon.scheduler.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import kr.co.zlgoon.scheduler.vo.SchedulerVo;

@Repository
@Mapper
public interface SchedulerRepository {
	
	List<SchedulerVo> getZlgoonSchedulerList() throws Exception;
	
	SchedulerVo getZlgoonSchedulerBySchedulerName() throws Exception;
	
	int updateRunningStatusBySchedulerName(SchedulerVo schedulerVo) throws Exception; 
	
	int updateRunningInfoBySchedulerName(SchedulerVo schedulerVo) throws Exception;
	
}
