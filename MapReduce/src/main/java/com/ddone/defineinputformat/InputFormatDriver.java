package com.ddone.defineinputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Cluster;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/7-01:53
 */
public class InputFormatDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(InputFormatDriver.class);
        job.setMapperClass(InputFormatMapper.class);
        job.setReducerClass(InputFormatReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        job.setInputFormatClass(MyInputFormat.class);
        job.setOutputKeyClass(SequenceFileOutputFormat.class);

        FileInputFormat.setInputPaths(job,new Path("input/in"));
        FileOutputFormat.setOutputPath(job,new Path("input/output"));

        job.waitForCompletion(true);
    }
}
