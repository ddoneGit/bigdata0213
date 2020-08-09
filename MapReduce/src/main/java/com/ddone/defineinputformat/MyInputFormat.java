package com.ddone.defineinputformat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-00:24
 * 1.继承FileInputFormat
 * 2.重写createRecorderReader
 * 重写isSplitable
 * 3.自定义RecordReader
 */

public class MyInputFormat extends FileInputFormat<Text, BytesWritable> {

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        MyRecordReader recordReader = new MyRecordReader();
        recordReader.initialize(split, context);
        //初始化配置
        return recordReader;
    }
}
