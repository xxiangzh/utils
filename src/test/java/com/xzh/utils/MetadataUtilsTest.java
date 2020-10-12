package com.xzh.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

public class MetadataUtilsTest {

    public static void main(String[] args) {
        List<File> fileList = FileUtil.getFileList("E:\\AAA", false);
        for (File file : fileList) {
            String dateTime = MetadataUtils.getDateTime(file);
            if (StringUtils.isBlank(dateTime)) {
                continue;
            }
            MetadataUtils.rename(file, dateTime);
        }
    }
}