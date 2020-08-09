package com.ddone.combiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-17:05
 */
public class CombinerDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(CombinerDriver.class);
        job.setMapperClass(CombinerMapper.class);
        job.setReducerClass(CombinerReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        job.setCombinerClass(CombinerReducer.class);

        FileInputFormat.setInputPaths(job,new Path("input/kv.txt"));
        FileOutputFormat.setOutputPath(job,new Path("input/output1"));

        job.waitForCompletion(true);
    }
}
