package com.ddone.hadoopserial;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/2-23:42
 *
 * 1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
 *
 * 写出的是 手机号 上行流量 下行流量
 */
public class FlowMapper extends Mapper<LongWritable, Text, Text,FlowBean> {
    private Text outK = new Text();
    private FlowBean outV = new FlowBean();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println(value.toString());
        String[] splits = value.toString().split("\t");
        outV.setUpFlow(Long.parseLong(splits[splits.length-3]));
        outV.setDownFlow(Long.parseLong(splits[splits.length-2])); //中间没有数据,从后面来,后面一定有
        outK.set(splits[1]);
        context.write(outK,outV);
    }
}
