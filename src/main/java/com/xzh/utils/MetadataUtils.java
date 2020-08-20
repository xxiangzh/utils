package com.xzh.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具
 *
 * @author 向振华
 * @date 2020/08/19 14:08
 */
public class MetadataUtils {

    public static void main(String[] args) {
        System.out.println("------------------------");
        List<File> fileList = FileUtil.getFileList("E:\\AAA", false);
        for (File file : fileList) {
            String dateTime = getDateTime(file);
            if (StringUtils.isBlank(dateTime)) {
                continue;
            }
            rename(file, dateTime);
        }
    }

    /**
     * 重命名
     *
     * @param file
     * @param dateTime
     */
    public static void rename(File file, String dateTime) {
        dateTime = dateTime + "_";
        String filePath = file.getPath();
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String oldFileNameNoType = fileName.substring(0, fileName.lastIndexOf("."));
        if (oldFileNameNoType.contains(dateTime)) {
            return;
        }
        // 创建新文件名
        String newPathNoName = filePath.replace(fileName, "");
        String newFileName = dateTime + oldFileNameNoType + fileType;
        String newPath = newPathNoName + newFileName;
        // 重命名
        file.renameTo(new File(newPath));
    }

    /**
     * 获取拍摄日期
     *
     * @return
     */
    public static String getDateTime(File file) {
        List<Tag> tagList = getTagList(file);
        if (CollectionUtils.isEmpty(tagList)) {
            return null;
        }
        for (Tag tag : tagList) {
            String directoryName = tag.getDirectoryName();
            String tagName = tag.getTagName();
            String description = tag.getDescription();
            // jpeg
            if ("Exif IFD0".equals(directoryName) && "Date/Time".equals(tagName)) {
                return DateUtils.stringToString(description, "yyyy:MM:dd HH:mm:ss", "yyyy_MM_dd_HH_mm_ss");
            }
            // png
            if ("File".equals(directoryName) && "File Modified Date".equals(tagName)) {
                return DateUtils.stringToString(description, "E MMM dd HH:mm:ss +08:00 yyyy", "yyyy_MM_dd_HH_mm_ss");
            }
            // mp4
            else if ("MP4 Video".equals(directoryName) && "Creation Time".equals(tagName)) {
                return DateUtils.stringToString(description, "E MMM dd HH:mm:ss +08:00 yyyy", "yyyy_MM_dd_HH_mm_ss");
            }
            // mov
            else if ("QuickTime Video".equals(directoryName) && "Creation Time".equals(tagName)) {
                return DateUtils.stringToString(description, "E MMM dd HH:mm:ss +08:00 yyyy", "yyyy_MM_dd_HH_mm_ss");
            }
        }
        return null;
    }

    /**
     * 获取所有标签
     *
     * @param file
     * @return
     */
    public static List<Tag> getTagList(File file) {
        List<Tag> tagList = new ArrayList<>();
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (metadata != null) {
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    //System.out.println(tag);
                    tagList.add(tag);
                }
            }
        }
        return tagList;
    }
}