package com.ddone.sortandpartition;

import com.ddone.partitioner.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author ddone
 * @date 2020/8/6-09:51
 */
public class MyPartitioner1 extends Partitioner<FlowBean1, Text> {
    public int getPartition(FlowBean1 key, Text value, int numPartitions) {
        int partition = 4;
        String prefix = value.toString().substring(0, 3);
        if ("136".equals(prefix)) {
            partition = 0;
        } else if ("137".equals(prefix)) {
            partition = 1;
        } else if ("138".equals(prefix)) {
            partition = 2;
        } else if ("139".equals(prefix)) {
            partition = 3;
        }
        return partition;
    }
}
