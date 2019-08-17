package kr.co.zlgoon.scheduler.vo;

import java.util.List;
import java.util.Map;

public class ResultListVo extends ResultVo {
	
	private List<Map<String, String>> failList;

	
	public List<Map<String, String>> getFailList() {
		return failList;
	}

	public void setFailList(List<Map<String, String>> failList) {
		this.failList = failList;
	}

}
