package com.ddone.etl;

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
 * @date 2020/8/8-21:40
 */
public class EtlRecordWriter extends RecordWriter<Text, NullWritable> {
    FSDataOutputStream fos;
    public EtlRecordWriter(TaskAttemptContext job) throws IOException {
        FileSystem fs = FileSystem.get(job.getConfiguration());
         fos= fs.create(new Path("input/etl.txt"));
    }

    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        fos.write(key.toString().getBytes());

    }

    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        fos.close();
    }
}
