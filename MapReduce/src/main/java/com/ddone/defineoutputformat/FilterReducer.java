package com.ddone.defineoutputformat;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/7-00:11
 */
public class FilterReducer extends Reducer<Text, NullWritable,Text,NullWritable> {
    Text outK = new Text();
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        String line = key.toString()+"\r\n";
//        System.out.println(line);
        outK.set(line);
        context.write(outK,NullWritable.get());
    }
}
