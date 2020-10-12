package com.xzh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具
 *
 * @author: 向振华
 * @date: 2019/11/19 10:00
 */
public class FileUtil {

    private static List<File> fileList = new ArrayList<File>();

    /**
     * 获取文件列表
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param isAll                 是否获取全部文件，true获取全部，false只获取路径下的文件，不包括路径下子文件夹的文件
     * @return 返回文件列表
     */
    public static List<File> getFileList(String sourceFolderDirectory, boolean isAll) {
        File dir = new File(sourceFolderDirectory);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            if (file.isDirectory() && isAll) {
                getFileList(file.getAbsolutePath(), true);
            } else if (file.isDirectory()) {
                continue;
            } else {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 文件复制
     *
     * @param absolutePath          源文件路径
     * @param targetFolderDirectory 目标文件夹目录
     */
    public static void copy(String absolutePath, String targetFolderDirectory) {
        File dir = new File(targetFolderDirectory);
        if (!dir.isDirectory()) {
            //如果目录不存在则创建
            dir.mkdir();
        }
        String newFileName = absolutePath.substring(absolutePath.lastIndexOf("\\") + 1);
        targetFolderDirectory += File.separator + newFileName;
        try {
            FileInputStream fis = new FileInputStream(absolutePath);
            FileOutputStream fos = new FileOutputStream(targetFolderDirectory);
            byte[] datas = new byte[1024 * 8];
            int len;
            while ((len = fis.read(datas)) != -1) {
                fos.write(datas, 0, len);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据关键字复制
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param targetFolderDirectory 目标文件夹目录
     * @param key                   关键字 （为null或""时 复制所有）
     */
    public static void copyByKey(String sourceFolderDirectory, String targetFolderDirectory, String key) {
        List<File> fileList = getFileList(sourceFolderDirectory, true);
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (key != null && !file.getAbsolutePath().contains(key)) {
                continue;
            }
            copy(file.getAbsolutePath(), targetFolderDirectory);
        }
    }

    /**
     * 排除关键字复制
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param targetFolderDirectory 目标文件夹目录
     * @param keys                  关键字数组
     */
    public static void copyWithoutKey(String sourceFolderDirectory, String targetFolderDirectory, String... keys) {
        if (keys == null) {
            return;
        }
        List<File> fileList = getFileList(sourceFolderDirectory, true);
        if (fileList == null) {
            return;
        }
        boolean flag = true;
        for (File file : fileList) {
            for (String key : keys) {
                if (file.getAbsolutePath().toUpperCase().contains(key.toUpperCase())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                copy(file.getAbsolutePath(), targetFolderDirectory);
            } else {
                flag = true;
            }
        }
    }
}
