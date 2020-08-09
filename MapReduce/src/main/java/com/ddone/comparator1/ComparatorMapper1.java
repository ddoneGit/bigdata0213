package com.ddone.comparator1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-22:29
 */
public class ComparatorMapper1 extends Mapper<LongWritable, Text,OrderInfo1, NullWritable> {

    OrderInfo1 outK = new OrderInfo1();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split("\t");
        outK.setOrder_id(arr[0]);
        outK.setSpu_id(arr[1]);
        outK.setAmount(Double.parseDouble(arr[2]));
        context.write(outK, NullWritable.get());
    }
}
