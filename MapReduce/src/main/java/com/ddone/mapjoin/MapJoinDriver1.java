package com.ddone.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;

/**
 * @author ddone
 * @date 2020/8/8-20:30
 *
 * 添加自定义OutPutFormat
 */
public class MapJoinDriver1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(MapJoinDriver1.class);
        job.setMapperClass(MapJoinMapper1.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setOutputFormatClass(MapJoinOutPutFormat.class);

        FileInputFormat.setInputPaths(job,new Path("input/map/order.txt"));
        FileOutputFormat.setOutputPath(job,new Path("input/output1"));

        //设置CacheFile,设置ReduceTask个数为0

        job.setCacheFiles(new URI[]{URI.create("input/map/pd.txt")});
        job.setNumReduceTasks(0);

        job.waitForCompletion(true);
    }
}
