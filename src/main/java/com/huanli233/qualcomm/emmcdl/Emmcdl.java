package com.huanli233.qualcomm.emmcdl;

import java.io.File;

import com.huanli233.qualcomm.base.Base;
import com.huanli233.qualcomm.exception.InitializationFailedException;

/**
 * emmcdl.exe相关操作类
 */
public class Emmcdl extends Base {
	
	public Emmcdl(File tempDir) throws InitializationFailedException {
		super(tempDir);
	}

	@Override
	protected boolean initBinFile() {
		return true;
	}

	@Override
	protected String getBinFileName() {
		return "emmcdl.exe";
	}
	
}
