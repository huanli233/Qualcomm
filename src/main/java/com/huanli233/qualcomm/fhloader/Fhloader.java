package com.huanli233.qualcomm.fhloader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.huanli233.qualcomm.base.Base;
import com.huanli233.qualcomm.bean.BaseResult;
import com.huanli233.qualcomm.exception.InitializationFailedException;
import com.huanli233.utils.FileUtil;
import com.huanli233.utils.CmdUtil.ExecResult;

public class Fhloader extends Base {
	
	File binFile;
	int comNum;
	boolean isReset = false;
	
	public Fhloader(File tempDir) throws InitializationFailedException {
		super(tempDir);
		this.binFile = new File(tempDir, "fh_loader.exe");
	}
	
	public boolean setBinFile(File binFile) {
		if (!binFile.exists() || !binFile.isFile()) {
			return false;
		}
		this.binFile = binFile;
		return true;
	}

	/**
	 * @return isReset
	 */
	public boolean isReset() {
		return isReset;
	}

	/**
	 * @param isReset 要设置的 isReset
	 */
	public void setReset(boolean isReset) {
		this.isReset = isReset;
	}

	@Override
	public ExecResult execCmd(String... args) {
		String[] mArgs = {"-p=" + String.format("\\\\.\\COM%d", comNum), "--noprompt", "--showpercentagecomplete", isReset ? "--noreset" : "--reset"};
		String[] cmd = new String[args.length + mArgs.length];
		for (int i = 0; i < mArgs.length; i++) {
			cmd[i] = mArgs[i];
		}
		for (int i = 0; i < args.length; i++) {
			cmd[i + mArgs.length] = args[i];
		}
		ExecResult result = super.execCmd(cmd);
		logger.info(result.getOutput());
		return result;
	}

	@Override
	protected String getBinFileName() {
		return binFile.getAbsolutePath();
	}

	@Override
	protected boolean initBinFile() {
		if (!getBinFile().exists()) {
			FileUtil.writeInputStreamToFile(getBinFile(), getClass().getResourceAsStream("/bin/fh_loader.exe"));
		} else if (!getBinFile().isFile()) {
			getBinFile().delete();
			FileUtil.writeInputStreamToFile(getBinFile(), getClass().getResourceAsStream("/bin/fh_loader.exe"));
		}
		return (getBinFile().exists()) && (getBinFile().isFile());
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
	
	public BaseResult<Void> write(File img, long start, long size) {
		ExecResult execResult = execCmd("--search_path=" + String.format("\"%s\"", img.getParentFile().getAbsolutePath()), "--lun=0", String.format("--sendimage=\"%s\"", img.getName()), String.format("--start_sectorsx=%d", start), String.format("--num_sectorsx=%d", size), "--zlpawarehost=1", "--memoryname=emmc");
		return new BaseResult<Void>(execResult.getExitCode(), null);
	}
	
	public BaseResult<Void> read(File img, long start, long size) {
		ExecResult execResult = execCmd("--convertprogram2read", "--search_path=" + String.format("\"%s\"", img.getParentFile().getAbsolutePath()), "--lun=0", String.format("--sendimage=\"%s\"", img.getName()), String.format("--start_sectorsx=%d", start), String.format("--num_sectorsx=%d", size), "--zlpawarehost=1", "--memoryname=emmc");
		return new BaseResult<Void>(execResult.getExitCode(), null);
	}
	
	public BaseResult<Void> sendXml(File xml) {
		ExecResult execResult = execCmd("--search_path=" + String.format("\"%s\"", xml.getParentFile().getAbsolutePath()), "--lun=0", String.format("--sendxml=\"%s\"", xml.getName()), "--zlpawarehost=1", "--memoryname=emmc");
		return new BaseResult<Void>(execResult.getExitCode(), null);
	}
	
	public BaseResult<Void> sendMultiXml(File... xmls) {
		List<String> xmlNames = new LinkedList<>();
		String xmlPath = null;
		for (int i = 0; i < xmls.length; i++) {
			if (xmlPath != null && !xmlPath.equals(xmls[i].getParentFile().getAbsolutePath())) {
				return new BaseResult<Void>(-1, null);
			}
			xmlPath = xmls[i].getParentFile().getAbsolutePath();
			xmlNames.add(xmls[i].getAbsolutePath());
		}
		String xml = "";
		for (int i = 0; i < xmlNames.size(); i++) {
			xml += xmlNames.get(i);
			if (i != (xmlNames.size() - 1)) {
				xml += ",";
			}
		}
		ExecResult execResult = execCmd("--search_path=" + String.format("\"%s\"", xmlPath), "--lun=0", String.format("--sendxml=\"%s\"", xml), "--zlpawarehost=1", "--memoryname=emmc");
		return new BaseResult<Void>(execResult.getExitCode(), null);
	}
	
}
