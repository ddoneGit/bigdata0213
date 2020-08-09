package com.ddone.mapjoin;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

/**
 * @author ddone
 * @date 2020/8/8-20:30
 */

/**
 *
 * 使用mapperJoin
 */
public class MapJoinMapper1 extends Mapper<LongWritable, Text,Text, NullWritable> {
    //用于存放缓冲的数据
    HashMap<String, String> cacheMap = new HashMap<String, String>();
    //定义输出的key
    Text outK = new Text();
    //获取缓存数据
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获取缓冲文件路径,创建输出流,然后写出
        URI[] files = context.getCacheFiles();
        //因为只有一个文件,所以第一个文件就是缓存的数据
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream fsis = fs.open(new Path(files[0]));
        //使用bufferReader来读取数据
        BufferedReader br = new BufferedReader(new InputStreamReader(fsis, "utf-8"));
        String line ;
        while ((line = br.readLine())!=null){
            String[] split = line.split("\t");
            cacheMap.put(split[0],split[1]);
        }
        IOUtils.closeStream(br);
    }

    //重新出了数据够拼接写出
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] splits = value.toString().split("\t");
        String pname = cacheMap.get(splits[1]);
        String outStr = splits[0] + "\t" + pname + "\t" + splits[1] + "\t" + splits[2]+"\r\n";
//        String outStr = splits[0] + "\t" + pname + "\t" + splits[1] + "\t" + splits[2];
        outK.set(outStr);
        context.write(outK,NullWritable.get());

    }
}
