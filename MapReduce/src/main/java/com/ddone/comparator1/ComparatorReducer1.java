package com.ddone.comparator1;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-22:34
 */
public class ComparatorReducer1 extends Reducer<OrderInfo1, NullWritable,OrderInfo1, NullWritable> {
    @Override
    protected void reduce(OrderInfo1 key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key,NullWritable.get()); //只写最先来的一个
    }
}
