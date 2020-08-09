package com.ddone.combine;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/7/31-08:29
 */
public class DraftDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置Driver,Mapper,Reducer Jar
        job.setJarByClass(DraftDriver.class);
        job.setMapperClass(DraftMapper.class);
        job.setReducerClass(DraftReducer.class);
        //3.设置Mapper输出K,V,最终输出K,V
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //*开启CombineTextInputFormat,在输入路径之前
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job,20971520);
        //4.设置输入输出路径
        FileInputFormat.setInputPaths(job, "input/s/");
        FileOutputFormat.setOutputPath(job, new Path("input/output1"));


        //5.启动并执行job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
