package com.huanli233.qualcomm.emmcdl.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceParser {
	public static List<Integer> parseDeviceInformation(String input) {
        List<Integer> comNumbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("9008 \\(COM(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String comNumberStr = matcher.group(1);
            int comNumber = Integer.parseInt(comNumberStr);
            comNumbers.add(comNumber);
        }

        return comNumbers;
    }
}
