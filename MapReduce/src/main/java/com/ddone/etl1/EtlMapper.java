package com.ddone.etl1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/8-23:07
 */
public class EtlMapper extends Mapper<LongWritable, Text,Text, NullWritable> {
    Text outK = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        LogBean log = parseLog(line);
        if (!log.isValid()) {
            return;
        }
        outK.set(log.toString());

    }

    //可以采用计数器查看过滤了多少条数据,保留了多少条数据
    //写一个规则
    private LogBean parseLog(String line) {
        String[] fields = line.split(" ");
        LogBean logBean = new LogBean();
        if (line.length() > 11) {
            logBean.setRemote_addr(fields[0]);
            logBean.setRemote_user(fields[1]);
            logBean.setTime_local(fields[3].substring(1));
            logBean.setRequest(fields[6]);
            logBean.setStatus(fields[8]);
            logBean.setBody_bytes_sent(fields[9]);
            logBean.setHttp_referer(fields[10]);
            if (fields.length > 12) {
                logBean.setHttp_user_agent(fields[11] + " "+ fields[12]);
            }else {
                logBean.setHttp_user_agent(fields[11]);
            }

            // 大于400，HTTP错误
            if (Integer.parseInt(logBean.getStatus()) >= 400) {
                logBean.setValid(false);
            }
        }else {
            logBean.setValid(false);
        }

        return logBean;
    }
}