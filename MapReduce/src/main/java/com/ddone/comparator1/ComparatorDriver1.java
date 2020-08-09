package com.ddone.comparator1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-22:42
 */
public class ComparatorDriver1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(ComparatorDriver1.class);
        job.setMapperClass(ComparatorMapper1.class);
        job.setReducerClass(ComparatorReducer1.class);

        job.setMapOutputKeyClass(OrderInfo1.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(OrderInfo1.class);
        job.setOutputValueClass(NullWritable.class);

        //设置Reduce端的分组
        job.setGroupingComparatorClass(OrderComparator.class);

        FileInputFormat.setInputPaths(job,new Path("input/GroupingComparator.txt"));
        FileOutputFormat.setOutputPath(job,new Path("input/output1"));

        job.waitForCompletion(true);
    }
}
