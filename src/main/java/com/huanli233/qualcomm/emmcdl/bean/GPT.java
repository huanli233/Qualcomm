package com.huanli233.qualcomm.emmcdl.bean;

import java.util.HashMap;
import java.util.Map;

public class GPT {
	Map<String, Partition> gpt;

	public GPT(Map<String, Partition> gpt) {
		this.gpt = gpt;
	}
	
	public GPT(Partition... gpt) {
		this.gpt = new HashMap<>();
		for (int i = 0; i < gpt.length; i++) {
			this.gpt.put(gpt[i].getName(), gpt[i]);
		}
	}

	/**
	 * @return gpt
	 */
	public Map<String, Partition> getGpt() {
		return gpt;
	}

	/**
	 * @param gpt 要设置的 gpt
	 */
	public void setGpt(Map<String, Partition> gpt) {
		this.gpt = gpt;
	}
}
