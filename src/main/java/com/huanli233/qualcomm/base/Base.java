package com.huanli233.qualcomm.base;

import java.io.File;

import com.huanli233.log.DefaultLogger;
import com.huanli233.log.ILogger;
import com.huanli233.qualcomm.exception.InitializationFailedException;
import com.huanli233.utils.CmdUtil;
import com.huanli233.utils.CmdUtil.ExecResult;

public abstract class Base {
	
	/**
	 * 可执行文件存放路径
	 */
	protected File tempDir;
	
	/**
	 * logger
	 */
	protected ILogger logger;

	/**
	 * 构造函数
	 * @param tempDir 可执行文件存放路径
	 * @throws InitializationFailedException 
	 */
	protected Base(File tempDir) throws InitializationFailedException {
		this.tempDir = tempDir;
		logger = new DefaultLogger();
		if (!tempDir.exists() && !tempDir.mkdirs()) {
			throw new InitializationFailedException("can not create directory " + tempDir.getAbsolutePath());
		}
		if (!initBinFile()) {
			throw new InitializationFailedException("unable to initialize");
		}
	}
	
	/**
	 * @return logger
	 */
	public ILogger getLogger() {
		return logger;
	}
	
	/**
	 * @param logger 要设置的 logger
	 */
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}
	
	/**
	 * 执行可执行文件命令
	 * @return 命令结果
	 */
	protected ExecResult execCmd(String... args) {
		return execCmd(tempDir.getAbsoluteFile(), args);
	}
	
	/**
	 * 执行可执行文件命令
	 * @return 命令结果
	 */
	protected ExecResult execCmd(File path, String... args) {
		String[] cmd = new String[args.length + 1];
		cmd[0] = getBinFileName();
		for (int i = 0; i < args.length; i++) {
			cmd[i + 1] = args[i];
		}
		return CmdUtil.runExecutable(path.getAbsoluteFile(), cmd);
	}
	
	/**
	 * 获取可执行文件的File对象
	 * @return 可执行文件File对象
	 */
	protected File getBinFile() {
		return new File(tempDir, getBinFileName());
	}
	
	/**
	 * 获取可执行文件名称(包含后缀)
	 * @return 可执行文件名称
	 */
	protected abstract String getBinFileName() ;
	
	/**
	 * 初始化可执行文件
	 */
	protected abstract boolean initBinFile();
	
}
