package com.ddone.etl1;

import com.ddone.etl.EtlOutputFormat;
import com.ddone.etl.LogMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/8-21:33
 */
public class EtlDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(EtlDriver.class);
        job.setMapperClass(LogMapper.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        job.setOutputFormatClass(EtlOutputFormat.class);
        FileInputFormat.setInputPaths(job,new Path("input/etl/web.log"));
        FileOutputFormat.setOutputPath(job,new Path("input/output"));

        job.setNumReduceTasks(0);

        job.waitForCompletion(true);
    }
}
