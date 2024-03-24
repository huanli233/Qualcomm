package com.huanli233.qualcomm.emmcdl;

import java.io.File;

import com.huanli233.qualcomm.base.Base;
import com.huanli233.qualcomm.bean.BaseResult;
import com.huanli233.qualcomm.emmcdl.bean.GPT;
import com.huanli233.qualcomm.emmcdl.parser.PartitionParser;
import com.huanli233.qualcomm.exception.InitializationFailedException;
import com.huanli233.utils.FileUtil;
import com.huanli233.utils.CmdUtil.ExecResult;

/**
 * emmcdl.exe相关操作类
 */
public class Emmcdl extends Base {
	
	int comNum = 0;
	File firehose;
	
	public Emmcdl(File tempDir) throws InitializationFailedException {
		super(tempDir);
	}

	/**
	 * @return firehose
	 */
	public File getFirehose() {
		return firehose;
	}

	/**
	 * @param firehose 要设置的 firehose
	 */
	public void setFirehose(File firehose) {
		this.firehose = firehose;
	}

	/**
	 * @return comNum
	 */
	public int getComNum() {
		return comNum;
	}

	/**
	 * @param comNum 要设置的 comNum
	 */
	public void setComNum(int comNum) {
		this.comNum = comNum;
	}
	
	@Override
	protected boolean initBinFile() {
		if (!getBinFile().exists()) {
			FileUtil.writeInputStreamToFile(getBinFile(), getClass().getResourceAsStream("/bin/emmcdl.exe"));
		} else if (!getBinFile().isFile()) {
			getBinFile().delete();
			FileUtil.writeInputStreamToFile(getBinFile(), getClass().getResourceAsStream("/bin/emmcdl.exe"));
		}
		return (getBinFile().exists()) && (getBinFile().isFile());
	}
	
	@Override
	protected ExecResult execCmd(String... args) {
		String[] mArgs = {"-p", String.format("COM%d", comNum), "-f", firehose.getAbsolutePath()};
		String[] cmd = new String[args.length + mArgs.length];
		for (int i = 0; i < mArgs.length; i++) {
			cmd[i] = mArgs[i];
		}
		for (int i = 0; i < args.length; i++) {
			cmd[i + mArgs.length] = args[i];
		}
		return super.execCmd(cmd);
	}

	@Override
	protected String getBinFileName() {
		return "emmcdl.exe";
	}
	
	/**
	 * 读取分区表
	 * @return 分区表
	 */
	public BaseResult<GPT> readGPT() {
		ExecResult result = execCmd("-gpt");
		return new BaseResult<GPT>(result.getExitCode(), new GPT(PartitionParser.parsePartitionData(result.getOutput())));
	}
	
}
