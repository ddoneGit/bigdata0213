package com.javase.review;

import java.io.File;

/**
 * @author ddone
 * @date 2020/7/24-00:50
 */
public class FileTestRecursion {
    public static void main(String[] args) {
        File file = new File("/ddone");
        listDirectAndFiles(file);

    }

    private static void listDirectAndFiles(File file) {
        if(file.isFile()){
            System.out.println(file.getAbsolutePath());
        }else {
            File[] files = file.listFiles();//是文件夹就listFiles
            for (File f : files) {//对每个文件进行判断
                listDirectAndFiles(f);
            }
        }
    }
}
