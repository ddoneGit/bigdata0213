package com.ddone.hadoopserial;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/2-23:42
 */
public class FlowReducer extends Reducer<Text,FlowBean,Text, FlowBean> {
//    private  Text outK = new Text() ;
    private FlowBean outV= new FlowBean();
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        long sum=0;
        long upFlow=0;
        long downFlow=0;
        for (FlowBean value : values) {
            upFlow+=value.getUpFlow();
            downFlow+=value.getDownFlow();
        }
        sum=upFlow+downFlow;
//        String str = key.toString()+"\t"+upFlow+"\t"+downFlow+"\t"+sum;
//        outK.set(str);
        outV.setDownFlow(downFlow);
        outV.setUpFlow(upFlow);
        outV.setSumFlow(sum);
        context.write(key,outV);
    }
}