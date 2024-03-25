package com.huanli233.qualcomm.emmcdl;

import java.io.File;
import java.util.List;

import com.huanli233.qualcomm.base.Base;
import com.huanli233.qualcomm.bean.BaseResult;
import com.huanli233.qualcomm.emmcdl.bean.GPT;
import com.huanli233.qualcomm.emmcdl.parser.DeviceParser;
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
	
	/**
	 * 读取指定分区
	 * @param partName 分区名
	 * @param output 输出文件
	 * @return
	 */
	public BaseResult<File> readData(String partName, File output) {
		ExecResult result = execCmd("-d", partName, "-o", output.getAbsolutePath());
		return new BaseResult<File>(result.getExitCode(), output);
	}
	
	/**
	 * 读取指定数据
	 * @param start 起始
	 * @param size 大小
	 * @param output 输出文件
	 * @return
	 */
	public BaseResult<File> readData(long start, long size, File output) {
		ExecResult result = execCmd("-d", String.valueOf(start), String.valueOf(size), "-o", output.getAbsolutePath());
		return new BaseResult<File>(result.getExitCode(), output);
	}
	
	/**
	 * 擦除指定分区
	 * @param partName 分区名
	 * @return
	 */
	public BaseResult<Void> eraseData(String partName) {
		ExecResult result = execCmd("-e", partName);
		return new BaseResult<Void>(result.getExitCode(), null);
	}
	
	/**
	 * 擦除指定数据
	 * @param start 起始
	 * @param size 大小
	 * @return
	 */
	public BaseResult<Void> eraseData(long start, long size) {
		ExecResult result = execCmd("-e", String.valueOf(start), String.valueOf(size));
		return new BaseResult<Void>(result.getExitCode(), null);
	}
	
	/**
	 * sendXml
	 * @param xmlFile xml文件路径
	 * @return
	 */
	public BaseResult<Void> sendXml(File xmlFile) {
		ExecResult result = execCmd("-xml", xmlFile.getAbsolutePath());
		return new BaseResult<Void>(result.getExitCode(), null);
	}
	
	/**
	 * 写入GPT
	 * @param partName 分区名
	 * @param file 文件路径
	 * @return
	 */
	public BaseResult<Void> writeData(String partName, File file) {
		ExecResult result = execCmd("-b", partName, file.getAbsolutePath());
		return new BaseResult<Void>(result.getExitCode(), null);
	}
	
	/**
	 * 获取edl设备列表
	 * @return
	 */
	public BaseResult<List<Integer>> getDevices() {
		ExecResult result = super.execCmd("-l");
		return new BaseResult<List<Integer>>(result.getExitCode(), DeviceParser.parseDeviceInformation(result.getOutput()));
	}
	
}
