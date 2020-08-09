package com.ddone.wcyarn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/7/31-01:23
 */
/*
key不变,只对value进行聚合
设定Value的初始值

toString(),get()
 */
public class WordCountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    IntWritable outValue = new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //聚合Key
        int sum=0;
        for (IntWritable value : values) {
            sum+=value.get();
        }
        outValue.set(sum);
        context.write(key,outValue);//写出Key,Value
    }
}
