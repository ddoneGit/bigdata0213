package com.ddone.partitioner;

import org.apache.hadoop.io.Writable;

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
public class FlowBean implements Writable, Comparable<FlowBean> {
    private long upFlow;
    private long downFlow;
    private long sumFlow;

    public FlowBean() {
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

    public int compareTo(FlowBean o) {
        return 0;
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
        return "FlowBean{" +
                "upFlow=" + upFlow +
                ", downFlow=" + downFlow +
                ", sumFlow=" + sumFlow +
                '}';
    }
}
