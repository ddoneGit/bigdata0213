package com.ddone.defineoutputformat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/7-00:08
 *
 *
 * Filter就是数据清洗
 *
 */
public class FilterMapper extends Mapper<LongWritable, Text,Text, NullWritable> {
    //制作读取数据
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        System.out.println(value);
        context.write(value,NullWritable.get());
    }
}
