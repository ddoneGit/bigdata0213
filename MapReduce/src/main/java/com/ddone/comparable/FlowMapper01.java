package com.ddone.comparable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/2-23:42
 * 对一次的数据进行加工
 */
public class FlowMapper01 extends Mapper<LongWritable,Text, FlowBean1, Text> {
    private FlowBean1 outK = new FlowBean1();
    private Text outV = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] splits = value.toString().split("\t");
        System.out.println(splits[1]);
        outK.setUpFlow(Long.parseLong(splits[1]));
        outK.setDownFlow(Long.parseLong(splits[2])); //中间没有数据,从后面来,后面一定有
        outK.setSumFlow(Long.parseLong(splits[3]));
        outV.set(splits[0]);
        System.out.println(splits[2]);
        context.write(outK,outV);
    }
}
