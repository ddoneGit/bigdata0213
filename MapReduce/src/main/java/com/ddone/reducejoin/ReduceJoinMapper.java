package com.ddone.reducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/8-00:31
 */
public class ReduceJoinMapper extends Mapper<LongWritable, Text,OrderBean, NullWritable> {
    OrderBean outK = new OrderBean();
    private String fileName;

    /**
     * 获取切片信息的数据
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSplit fs = (FileSplit) context.getInputSplit();
        fileName = fs.getPath().getName(); //获取切片的文件名

    }

    /**
     * 按照不同的文件,写出不同的数据
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split("\t");
        if("order.txt".equals(fileName)){
           outK.setId(arr[0]);
           outK.setPid(arr[1]);
           outK.setAmount(Integer.parseInt(arr[2]));
           outK.setpName("");
           context.write(outK,NullWritable.get());
        }else if ("pd.txt".equals(fileName)){
            outK.setPid(arr[0]);
            outK.setpName(arr[1]);
            outK.setAmount(0);
            outK.setId("");
            context.write(outK,NullWritable.get());
        }

    }
}
