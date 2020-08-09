package com.ddone.wcwithoutputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/7-00:58
 */
public class WcRecordWriter extends RecordWriter<Text, IntWritable> {
    FSDataOutputStream fsOs;
    public WcRecordWriter(TaskAttemptContext job) throws IOException {
        FileSystem fs = FileSystem.get(job.getConfiguration());
        fsOs = fs.create(new Path("input/t/wc.txt"));
    }

    public void write(Text key, IntWritable value) throws IOException, InterruptedException {
        fsOs.write((key.toString()+"\t"+value.toString()+"\n").getBytes());

    }

    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        fsOs.close();
    }
}
