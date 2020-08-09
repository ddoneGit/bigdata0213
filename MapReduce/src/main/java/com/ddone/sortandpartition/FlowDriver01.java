package com.ddone.sortandpartition;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/2-23:43
 */
public class FlowDriver01 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.创建job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置三个类
        job.setJarByClass(FlowDriver01.class);
        job.setMapperClass(FlowMapper01.class);
        job.setReducerClass(FlowReducer01.class);
        //3.设置map kv 输出,最终kv输出
        job.setMapOutputKeyClass(FlowBean1.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean1.class);

        job.setPartitionerClass(MyPartitioner1.class);
        job.setNumReduceTasks(5);


        //4.设置in/out路径
        FileInputFormat.setInputPaths(job,new Path("input/first-out.txt"));
        FileOutputFormat.setOutputPath(job,new Path("input/output1"));
        //5.执行
        job.waitForCompletion(true);
    }
}
