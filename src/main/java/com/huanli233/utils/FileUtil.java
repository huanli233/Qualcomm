package com.huanli233.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	
}
