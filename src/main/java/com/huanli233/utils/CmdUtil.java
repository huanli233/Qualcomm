package com.huanli233.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtil {
	
	public static class ExecResult {
	    private String output;
	    private int exitCode;

	    public ExecResult(String output, int exitCode) {
	        this.output = output;
	        this.exitCode = exitCode;
	    }

	    public String getOutput() {
	        return output;
	    }

	    public int getExitCode() {
	        return exitCode;
	    }
	}
	
	public static ExecResult runExecutable(File directory, String... args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(directory);
            processBuilder.command(args);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            return new ExecResult(output.toString(), exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ExecResult("", -1);
        }
    }
	
}
