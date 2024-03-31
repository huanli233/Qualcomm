package com.huanli233.qualcomm.qsaharaserver;

import java.io.File;

import com.huanli233.qualcomm.base.Base;
import com.huanli233.qualcomm.bean.BaseResult;
import com.huanli233.qualcomm.exception.InitializationFailedException;
import com.huanli233.utils.CmdUtil.ExecResult;
import com.huanli233.utils.FileUtil;

public class QSaharaServer extends Base {
	
	File binFile;

	public QSaharaServer(File tempDir) throws InitializationFailedException {
		super(tempDir);
		this.binFile = new File(tempDir, "QSaharaServer.exe");
	}
	
	public boolean setBinFile(File binFile) {
		if (!binFile.exists() || !binFile.isFile()) {
			return false;
		}
		this.binFile = binFile;
		return true;
	}
	
	@Override
	public ExecResult execCmd(String... args) {
		return super.execCmd(binFile.getParentFile().getAbsoluteFile(), args);
	}

	@Override
	protected String getBinFileName() {
		return binFile.getAbsolutePath();
	}

	@Override
	protected boolean initBinFile() {
		if (!getBinFile().exists()) {
			FileUtil.writeInputStreamToFile(getBinFile(), getClass().getResourceAsStream("/bin/QSaharaServer.exe"));
		} else if (!getBinFile().isFile()) {
			getBinFile().delete();
			FileUtil.writeInputStreamToFile(getBinFile(), getClass().getResourceAsStream("/bin/QSaharaServer.exe"));
		}
		return (getBinFile().exists()) && (getBinFile().isFile());
	}
	
	/**
	 * 
	 * @param com COM号
	 * @param fireHose 文件路径
	 * @return
	 */
	public BaseResult<Void> sendFirehose(int com, File fireHose) {
		ExecResult result = execCmd("-p", String.format("\\\\.\\COM%s", String.valueOf(com)), "-s", String.format("13:", fireHose.getAbsolutePath()));
		logger.info(result.getOutput());
		return new BaseResult<Void>(result.getExitCode(), null);
	}

}
