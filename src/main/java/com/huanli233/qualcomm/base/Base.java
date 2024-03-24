package com.huanli233.qualcomm.base;

import java.io.File;

public abstract class Base {
	
	/**
	 * 可执行文件存放路径
	 */
	protected File tempDir;

	/**
	 * 
	 * @param tempDir 可执行文件存放路径
	 */
	protected Base(File tempDir) {
		this.tempDir = tempDir;
		initBinFile();
	}

	/**
	 * 初始化可执行文件
	 */
	protected abstract void initBinFile();
}
