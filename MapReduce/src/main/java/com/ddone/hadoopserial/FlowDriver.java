package com.ddone.hadoopserial;

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
 * @date 2020/8/2-23:43
 */
public class FlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.创建job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置三个类
        job.setJarByClass(FlowDriver.class);
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        //3.设置map kv 输出,最终kv输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
//
//        job.setPartitionerClass(MyPartitioner.class);
//        job.setNumReduceTasks(2);


        //4.设置in/out路径
        FileInputFormat.setInputPaths(job,new Path("input/phone_data.txt"));
        FileOutputFormat.setOutputPath(job,new Path("input/output"));
        //5.执行
        job.waitForCompletion(true);
    }
}