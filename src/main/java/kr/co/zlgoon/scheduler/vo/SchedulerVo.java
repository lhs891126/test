package kr.co.zlgoon.scheduler.vo;

public class SchedulerVo {
	
	private String 	schedulerName;			//스케쥴 명
	private String	memo;					//스케쥴설명
	private String 	triggerType;			//트리거 타입
	private String 	triggerValue;			//트리거값
	private int		poolSize;				//풀사이즈
	private String 	lastExeDate;			//마지막 실행시간
	private long 	runningTime;			//마지막 수행 시간(ms)
	private String 	runningStatus;			//잡 프로세스 실행 상태
	private String 	jobStatus;				//잡 실행 상태
	private String 	use_yn;					//사용여부(Y/N)
	private String 	regDate;				//등록 일시
	private String	regId;					//등록자
	private String 	modDate;				//수정자
	private String  modId;					//수정일시
	private Object  schedulerBean;			//스케쥴 객체
	
	
	
	public String getLastExeDate() {
		return lastExeDate;
	}

	public void setLastExeDate(String lastExeDate) {
		this.lastExeDate = lastExeDate;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public Object getSchedulerBean() {
		return schedulerBean;
	}
	
	public void setSchedulerBean(Object schedulerBean) {
		this.schedulerBean = schedulerBean;
	}
	
	public String getSchedulerName() {
		return schedulerName;
	}
	
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getTriggerType() {
		return triggerType;
	}
	
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	
	public String getTriggerValue() {
		return triggerValue;
	}
	
	public void setTriggerValue(String triggerValue) {
		this.triggerValue = triggerValue;
	}
	
	public long getRunningTime() {
		return runningTime;
	}
	
	public void setRunningTime(long runningTime) {
		this.runningTime = runningTime;
	}
	
	public String getRunningStatus() {
		return runningStatus;
	}
	
	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public String getRegId() {
		return regId;
	}
	
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	public String getModDate() {
		return modDate;
	}
	
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	
	public String getModId() {
		return modId;
	}
	
	public void setModId(String modId) {
		this.modId = modId;
	}
	
}
