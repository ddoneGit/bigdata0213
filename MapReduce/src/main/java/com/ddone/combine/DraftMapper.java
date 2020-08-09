package com.ddone.combine;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/7/31-08:29
 */
public class DraftMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    Text outKey = new Text();
    IntWritable outValue = new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split(" ");
        for (String word : words) {
            outKey.set(word);
            context.write(outKey,outValue);
        }
    }
}
