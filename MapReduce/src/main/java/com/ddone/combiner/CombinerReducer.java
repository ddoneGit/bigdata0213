package com.ddone.combiner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-17:02
 */
public class CombinerReducer extends Reducer<Text, LongWritable,Text,LongWritable> {
    LongWritable outV = new LongWritable();
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (LongWritable value : values) {
            sum+=value.get();
        }
        outV.set(sum);
        context.write(key,outV);
    }
}
