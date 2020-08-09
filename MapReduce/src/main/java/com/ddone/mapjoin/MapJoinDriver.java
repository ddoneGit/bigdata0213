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
import java.net.URISyntaxException;

/**
 * @author ddone
 * @date 2020/8/8-20:20
 */
public class MapJoinDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration() ;
        Job job = Job.getInstance(conf);

        //不需要Reduce方法了
        job.setJarByClass(MapJoinDriver.class);
        job.setMapperClass(MapJoinMapper.class);

        //设置输出的kv
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置输入输出路径
        FileInputFormat.setInputPaths(job,new Path("input/map/order.txt"));
        FileOutputFormat.setOutputPath(job,new Path("input/output1"));

        //加载缓存数据,设置reduceTask个数为0
        job.setCacheFiles(new URI[]{new URI("input/map/pd.txt")});
        job.setNumReduceTasks(0);

        job.waitForCompletion(true);

    }
}
