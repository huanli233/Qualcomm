package com.huanli233.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {
	
	/**
	 * 从InputStream写入到文件
	 * @param filePath 目标文件
	 * @param inputStream InputStream
	 */
	public static void writeInputStreamToFile(File filePath, InputStream inputStream) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 移动文件
	 * @param sourceFile 源文件
	 * @param destinationFile 目标文件
	 */
	public static Throwable moveFile(File sourceFile, File destinationFile) {

        try {
            Files.move(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return null;
        } catch (IOException e) {
            return e;
        }
    }
	
}
