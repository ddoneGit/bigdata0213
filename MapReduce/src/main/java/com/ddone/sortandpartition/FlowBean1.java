package com.ddone.sortandpartition;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/2-23:19
 */
/*
Hadoop序列化
    1.实现Writable接口,事项两个方法,按顺序序列化和反序列化
    2.必须要有空参构造器

 */
public class FlowBean1 implements WritableComparable<FlowBean1> {
    private long upFlow;
    private long downFlow;
    private long sumFlow;

    public FlowBean1() {
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);


    }

    public void readFields(DataInput in) throws IOException {
        this.upFlow= in.readLong();
        this.downFlow =in.readLong();
        this.sumFlow = in.readLong();
    }

    /**
     * 按照总流量大小排序
     * @param o
     * @return
     */
    public int compareTo(FlowBean1 o) {

        int result;

        // 按照总流量大小，倒序排列
        if (sumFlow > o.getSumFlow()) {
            result = -1;
        }else if (sumFlow < o.getSumFlow()) {
            result = 1;
        }else {
            result = 0;
        }

        return result;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    @Override
    public String toString() {
        return upFlow+"\t"+downFlow+"\t"+sumFlow;
    }
}
