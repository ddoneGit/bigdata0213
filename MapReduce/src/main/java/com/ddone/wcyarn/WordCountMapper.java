package com.ddone.wcyarn;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/7/31-01:09
 */
/*
手写5遍

Key是上次已经读取到的值


Word Count
Word
Word
测试Key的值0
测试Key的值11
测试Key的值16
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    //定义写出的数据(String,Int) -> (str,1)
    Text keyOut = new Text();
    IntWritable valueOut = new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //处理读入的数据
        String[] splits = value.toString().split(" ");
        //写出数据
        for (String split : splits) {
            keyOut.set(split);
            context.write(keyOut,valueOut);
        }
        System.out.println("测试Key的值"+key.toString());
    }
}
//1.继承Mapper<KeyIn,ValueIn,KeyOut,ValueOut>四个泛型 输入字符串是ValueIn
//写出的数据时(str,1)
//定义写出数据的类型