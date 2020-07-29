package com.javase.review;

import java.io.File;

/**
 * @author ddone
 * @date 2020/7/24-19:36
 */
public class FileTest02 {
    public static void main(String[] args) {
        File file = new File("/ddone");
        Long size = fileLengh(file);
        System.out.println(size/1024/1024+"M");
    }

    private static Long fileLengh(File file) {
        Long size = 0L;
        if (file.isFile()) {
            return file.length();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                if(f.isFile()){
                   size+=f.length();
                }else {
                    size += fileLengh(f);
                }
            }


            return size;
        }
    }
}
