package com.ddone.reducejoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author ddone
 * @date 2020/8/8-00:41
 */
public class ReducerJoinReducer extends Reducer<OrderBean, NullWritable,OrderBean,NullWritable> {
    //pid相同进入同一个组
    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        // //第一条数据来自pd，之后全部来自order
        //通过第一条数据获取pname
        Iterator<NullWritable> iterator = values.iterator();
        iterator.next(); //第一条数据
        String pName = key.getpName();
        System.out.println(pName);
        //第一条数据有pid和pName
        while (iterator.hasNext()){
            iterator.next();
            key.setpName(pName);
            context.write(key,NullWritable.get());
        }

    }
}
