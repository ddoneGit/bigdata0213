package com.javase.review;

import java.io.File;
import java.io.IOException;

/**
 * @author ddone
 * @date 2020/7/24-19:50
 */
public class FileTest03 {
    public static void main(String[] args) throws IOException {
        File srcFile = new File("/ddone/file/hello.txt");
        srcFile.createNewFile();
        File destFile = new File(srcFile.getParent(), "abc.txt");
        File dirFile = new File("/ddone/file/test/xx");
        File dirAndFile1 = new File("/ddone/file/test/xx/jj.txt");
        File dirAndFile2 = new File("/ddone/file/test/kj.txt");
        dirAndFile1.createNewFile();
        dirAndFile2.createNewFile();
        dirFile.mkdirs();
        destFile.delete();
        destFile.createNewFile();
        System.out.println("创建成功");

//        deleteDirAndFiles(new File("/ddone/file/test"));
    }

    private static void deleteDirAndFiles(File file) {
        if(file.isFile()){
           file.delete();
        }else {
            File[] files = file.listFiles();
            for (File f : files) {
                if(f.isFile()){
                   f.delete();
                }else{
                    deleteDirAndFiles(f);
                }
                //先删除文件
                f.delete();
                //最后删除自己
            }
            file.delete();
            //最后删除自己
        }

    }
}
