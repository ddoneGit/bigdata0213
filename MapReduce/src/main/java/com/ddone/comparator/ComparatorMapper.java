package com.ddone.comparator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-22:29
 */
public class ComparatorMapper extends Mapper<LongWritable, Text,Text,OrderInfo> {

    Text outK = new Text();
    OrderInfo outV = new OrderInfo();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split("\t");
        outK.set(arr[0]);
        outV.setOrder_id(arr[0]);
        outV.setSpu_id(arr[1]);
        outV.setAmount(Double.parseDouble(arr[2]));
        context.write(outK,outV);
    }
}
