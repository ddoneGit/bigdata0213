package com.ddone.comparator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-22:34
 */
public class ComparatorReducer  extends Reducer<Text, OrderInfo,Text, DoubleWritable> {
    DoubleWritable outV = new DoubleWritable();
    @Override
    protected void reduce(Text key, Iterable<OrderInfo> values, Context context) throws IOException, InterruptedException {
        Double amount = 0.0;
        for (OrderInfo value : values) {
            if(value.getAmount()>amount){
               amount=value.getAmount();
            }
        }
        outV.set(amount);
        context.write(key,outV);
    }
}
