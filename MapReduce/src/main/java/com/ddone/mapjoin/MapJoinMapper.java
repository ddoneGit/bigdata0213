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
 * @date 2020/8/8-19:45
 */
public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    private HashMap<String, String> pMap = new HashMap<String, String>();
    private Text outK = new Text();

    //获取放进内存中的表信息,存入Map中
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        System.out.println(context.getCurrentKey());
        //从缓冲文件中找到pd.txt
        URI[] cacheFiles = context.getCacheFiles();
        Path path = new Path(cacheFiles[0]);//只有一个文件,取第一个
        FileSystem fs = FileSystem.get(context.getConfiguration());
        //获取文件系统以及获取文件输入流
        FSDataInputStream fsis = fs.open(path);
        //使用BufferReader一行一行读取数据
        BufferedReader br = new BufferedReader(new InputStreamReader(fsis, "utf-8"));
        String line;
        while ((line = br.readLine()) != null) {
            //读取出的一行一行数据
            String[] arr = line.split("\t");
            pMap.put(arr[0], arr[1]);
        }
        IOUtils.closeStream(br);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //任务开始前,将pd数据缓存进PMap
        String[] splits = value.toString().split("\t");
        String pname = pMap.get(splits[1]); //按照pid,或去pname
        outK.set(splits[0]+"\t"+pname+"\t"+splits[2]);//写出想要的数据
        context.write(outK,NullWritable.get());
    }
}
