package com.huanli233.qualcomm.tests;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import com.huanli233.qualcomm.bean.BaseResult;
import com.huanli233.qualcomm.emmcdl.Emmcdl;
import com.huanli233.qualcomm.emmcdl.bean.GPT;
import com.huanli233.qualcomm.emmcdl.bean.Partition;

public class EmmcdlTest {
	@Test
	public void testReadGPT() throws Exception {
		Emmcdl emmcdl = new Emmcdl(new File("temp"));
		emmcdl.setComNum(16);
		emmcdl.setFirehose(new File("E:\\test\\prog_emmc_firehose_8937_ddr_m8937.mbn"));
		BaseResult<GPT> result = emmcdl.readGPT();
		System.out.println(result.getCode());
		Map<String, Partition> gpt = result.getData().getGpt();
		for (Map.Entry<String, Partition> entry : gpt.entrySet()) {
			Partition val = entry.getValue();
			System.out.println(String.format("Name:%s Start:%d End:%d Size:%d", val.getName(), val.getStart(), val.getEnd(), val.getSize()));
		}
	}
}
