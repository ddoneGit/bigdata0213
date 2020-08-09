package com.ddone.etl;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/8-21:23
 *
 * 对日志长度小于11位的过滤
 */
public class LogMapper extends Mapper<LongWritable, Text,Text, NullWritable> {
    Text outKey = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        boolean flag = parseLog(line,context);
        if(!flag){
           return;
        }
        outKey.set(line+"\r\n");
        context.write(outKey,NullWritable.get());
    }
    //可以采用计数器查看过滤了多少条数据,保留了多少条数据
    private boolean parseLog(String line, Context context) {
        String[] splits = line.split(" ");
        if(splits.length>11){
            context.getCounter("map","ture").increment(1);
           return true;
        }

        context.getCounter("map","false").increment(1);
        return false;
    }


}
