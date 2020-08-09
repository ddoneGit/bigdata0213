package com.ddone.combiner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-16:59
 */
public class CombinerMapper extends Mapper<LongWritable, Text,Text,LongWritable> {
    Text outK = new Text();
    LongWritable outV = new LongWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split(" ");
        for (String s : arr) {
            outK.set(s);
            context.write(outK,outV);
        }
    }
}
