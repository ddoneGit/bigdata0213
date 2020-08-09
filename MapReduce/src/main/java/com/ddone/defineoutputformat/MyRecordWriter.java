package com.ddone.defineoutputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/7-00:16
 * 1.初始化构造器,创建输出流
 * 2.write方法写出数据
 * 3.关闭流
 *
 */

public class MyRecordWriter extends RecordWriter<Text, NullWritable> {

    FSDataOutputStream atguiguOs;
    FSDataOutputStream otherOs;
    public MyRecordWriter(TaskAttemptContext job) throws IOException {//外界传进来的job信息
        FileSystem fs ;
         fs = FileSystem.get(job.getConfiguration()); //从外界获取文件系统
        //        创建输出流
        atguiguOs  = fs.create(new Path("input/atguigu.txt"));
        otherOs   = fs.create(new Path("input/other.txt"));

    }

    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        System.out.println(key.toString());
        //判断,然后写出
        if(key.toString().contains("atguigu")){
            atguiguOs.write(key.toString().getBytes()); //写出数据
        }else {
            otherOs.write(key.toString().getBytes());
        }
    }
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            atguiguOs.close();
            otherOs.close();
    }
}
