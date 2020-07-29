package com.javase.review;


import java.io.*;

/**
 * @author ddone
 * @date 2020/7/24-20:50
 */
public class IOTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("/ddone/file/hello.txt");
//        fileReaderTest(file);
//        fileWriterTest(file);
        String srcFile = "/ddone/file/test/oos.dat";
        String destFile = "/ddone/file/test/oos.dat";
//        fileCopyWithFWAndFR(srcFile, destFile);
//        fileCopyWithFISAndFOS(srcFile, destFile);
//        fileCopyWithBISAndBOS(srcFile, destFile);
//        fileCopyWithBWAndBR(srcFile, destFile);
//        fileCopyWithBWAndBR01(srcFile, destFile);
//        changeCharSet(srcFile,destFile);
//        changeCharSetAndCopy(srcFile,destFile);
         fileObjectISAndOS(srcFile,destFile);
//            new File(destFile).createNewFile();





    }

    private static void fileObjectISAndOS(String srcFile, String destFile) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(srcFile));
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(destFile));
//        oos.writeByte();
//        oos.writeBoolean();
//        oos.writeObject(new String("Tom"));
//        oos.flush();
//        oos.close();
        String s = (String) ois.readObject();
        System.out.println(s);
        ois.close();

    }

    private static void changeCharSetAndCopy(String srcFile, String destFile) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(srcFile), "utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(destFile), "gbk");
        int len;
        char[] buff = new char[1024];
        while ((len=isr.read(buff))!=-1){
            osw.write(buff,0,len);
        }

        isr.close();
        osw.close();
    }

    private static void changeCharSet(String srcFile, String destFile) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(destFile), "gbk");
        int len;
        char[] buff=new char[1024];
        while ((len=isr.read(buff))!=-1){
            System.out.println(new String(buff,0,len));
        }
        isr.close();
    }

    private static void fileCopyWithBWAndBR01(String srcFile, String destFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(srcFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
        String data;
        System.out.println(br.readLine());
        while ((data=br.readLine()) != null){
            bw.write(data);
            bw.newLine(); //加上换行
        }
        bw.close();
        br.close();
    }

    private static void fileCopyWithBWAndBR(String srcFile, String destFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(srcFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
        int len;
        char[] buff = new char[1024];
        while ((len=br.read(buff))!= -1){
            bw.write(buff,0,len);
        }
        bw.close();
        br.close();
    }

    private static void fileCopyWithBISAndBOS(String srcFile, String destFile) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        int len;
        byte[] buff=new byte[1024];
        while ((len=bis.read(buff))!=-1){
            bos.write(buff,0,len);
        }
        bis.close();
        bos.close();
    }

    private static void fileCopyWithFISAndFOS(String srcFile, String destFile) throws IOException {
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        int len;
        byte[] buff = new byte[1024];
        while ((len=fis.read(buff)) != -1){
            fos.write(buff,0,len);
        }
        fis.close();
        fos.close();

    }

    private static void fileCopyWithFWAndFR(String srcFile, String destFile) {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader(srcFile);
            fw = new FileWriter(destFile);
            int len;
            char[] buff = new char[1024];
            while ((len = fr.read(buff)) != -1) {
                fw.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void fileWriterTest(File file) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true); //true,是追加,默认是false,会覆盖
            fw.write("这是一个测试文件 ".toCharArray());//写入的必须是字符数组
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void fileReaderTest(File file) {
//        //2.创建文件流
        FileReader fr = null;
        try {
            fr = new FileReader(file);
//        //3.读取数据
//        int len;
//        while ((len=fr.read())!=-1){
//            System.out.println((char)fr.read());
//        }
//        //4.关闭流
//        fr.close();//一定要关闭
//    }
            char[] buff = new char[1024];
            int len;
            while ((len = fr.read(buff)) != -1) {
                String str = new String(buff, 0, len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
