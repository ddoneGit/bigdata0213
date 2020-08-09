package com.ddone.mapjoin;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * @author ddone
 * @date 2020/8/8-20:52
 */
public class MapperJoinRecordWriter extends RecordWriter<Text, NullWritable> {
    FileSystem fs;
    FSDataOutputStream fos;
    BufferedWriter bw;
    public MapperJoinRecordWriter(TaskAttemptContext job) throws IOException {
        //获取输出流并且输出
        fs= FileSystem.get(job.getConfiguration());
        fos= fs.create(new Path("input/map/result.txt"));

//       bw = new BufferedWriter(new OutputStreamWriter(fos, "utf8"));
    }

    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
//       bw.write(key.toString());
//       bw.newLine();
        fos.write(key.toString().getBytes());
    }

    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
//        IOUtils.closeStream(bw);
        IOUtils.closeStream(fos);
    }
}