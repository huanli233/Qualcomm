package com.huanli233.qualcomm.emmcdl.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huanli233.qualcomm.bean.Partition;

public class PartitionParser {
    
    public static Map<String, Partition> parsePartitionData(String str) {
    	String[] data = str.split("\\r?\\n");
        Map<String, Partition> partitions = new HashMap<>();
        
        for (String line : data) {
            Partition partition = parsePartitionLine(line);
            if (partition != null) {
                partitions.put(partition.getName(), partition);
            }
        }
        
        return partitions;
    }
    
    private static Partition parsePartitionLine(String line) {
        Pattern pattern = Pattern.compile("Partition Name: (\\w+) Start LBA: (\\d+) Size in LBA: (\\d+)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String name = matcher.group(1);
            int startLba = Integer.parseInt(matcher.group(2));
            int sizeInLba = Integer.parseInt(matcher.group(3));
            
            return new Partition(name, startLba, sizeInLba);
        }
        
        return null;
    }
}
