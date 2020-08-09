package com.ddone.wc;

/**
 * @author ddone
 * @date 2020/7/31-01:28
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 *
 * 步骤比较多
 *总共5步 取出首尾,只有3步
 *设置jar包需要用类名.class
 *输入和输出路径比较特殊 FileInputFormat/FileOutPutFormat
 * FileInputFormat的包必须是 org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
 * 提交任务 job.
 *  job.waitForCompletion(true);
 */
public class WordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.获取job 提交任务需要 JOb
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置Driver jar加载路径(setJar),全类名,设置Map类设置Reduce类 全类名
        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //3.设置Map输出和Reduce输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //4.设置输入输出路径
        FileInputFormat.setInputPaths(job,args[0]);
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //5.提交任务
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
