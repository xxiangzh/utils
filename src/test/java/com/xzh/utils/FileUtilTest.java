package com.xzh.utils;

public class FileUtilTest {

    public static void main(String[] args) {
        FileUtil.copyWithoutKey("E:\\ADOCUMENT", "E:\\新建文件夹", new String[]{".png", ".jpg"});
    }
}