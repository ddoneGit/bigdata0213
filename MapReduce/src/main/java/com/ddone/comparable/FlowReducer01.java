package com.ddone.comparable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/2-23:42
 */
public class FlowReducer01 extends Reducer<FlowBean1, Text,Text, FlowBean1> {
//    private  Text outK = new Text() ;
    //作用就是交换key和value的位置

    @Override
    protected void reduce(FlowBean1 key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {//多个手机号,一个一个写出
            context.write(value,key);
        }
    }
}
