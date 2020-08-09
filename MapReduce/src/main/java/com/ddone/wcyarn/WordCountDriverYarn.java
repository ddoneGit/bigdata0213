package com.ddone.wcyarn;

/**
 * @author ddone
 * @date 2020/7/31-01:28
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.MRConfig;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.io.IOException;

/**
 *
 * 步骤比较多
 *总共5步 取出首尾,只有3步
 *设置jar包需要用类名.class
 *输入和输出路径比较特殊 FileInputFormat/FileOutPutFormat
 * FileInputFormat的包必须是 org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
 * 提交任务 job.
 *  job.waitForCompletion(true);
 */
public class WordCountDriverYarn {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.获取job 提交任务需要 JOb
        Configuration conf = new Configuration();
            //提交job到Yarn
        /**
         * 使用配置文件中的变量,代码中都可以设置
         * 四个参数
         * CommonConfigurationKeysPublic-->core-site
         * FileSystem.default_FS : hdfs://hadoop102:9820 对应core-site.xml
         * MRConfig.framework_name yarn mapred-site.xml
         * mrConfig.MAPREDUCE_APP_SUBMISSION_CROSS_PLATFORM
         * YarnConfiguration --对应的是yarn-site
         * DFSConfigKeys --对应的是 hdfs-site.xml
         */
        //CommonConfigurationKeysPublic
        conf.set(CommonConfigurationKeysPublic.FS_DEFAULT_NAME_KEY,"hdfs://hadoop102:9820");
//        conf.set(FileSystem.DEFAULT_FS,"hdfs://hadoop102:9820");
        conf.set(MRConfig.FRAMEWORK_NAME,"yarn");
            //设置允许在yarn上
        conf.set(MRConfig.MAPREDUCE_APP_SUBMISSION_CROSS_PLATFORM,"true");
            //
        conf.set(YarnConfiguration.RM_HOSTNAME,"hadoop103");

        Job job = Job.getInstance(conf);
        //2.设置Driver jar加载路径(setJar),全类名,设置Map类设置Reduce类 全类名
            //需要设置jar包
        job.setJar("/常用文件/复习笔记Txt版本/Hadoop生态/MapReduce-WordCount-On-Yarn.jar");
//        job.setJarByClass(WordCountDriverYarn.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //3.设置Map输出和Reduce输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //4.设置输入输出路径
        FileInputFormat.setInputPaths(job,args[0]);
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //5.提交任务
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
