package com.ddone.reducejoin;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/8-00:14
 */
public class OrderBean implements WritableComparable<OrderBean> {
    private String id ;
    private  String pid;
    private Integer amount;
    private  String pName;

    /**
     *
     * 先按照pid排序,然后再按照pName进行排序
     * @param o
     * @return
     */
    public int compareTo(OrderBean o) {
        if(this.pid.compareTo(o.pid)==0){
           return -this.pName.compareTo(o.pName);
        }
        return this.pid.compareTo(o.pid);
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(id);
        out.writeUTF(pid);
        out.writeInt(amount);
        out.writeUTF(pName);

    }

    public void readFields(DataInput in) throws IOException {
        this.id = in.readUTF();
        this.pid = in.readUTF();
        this.amount = in.readInt();
        this.pName = in.readUTF();
    }

    public OrderBean() {
    }

    @Override
    public String toString() {
        return id+"\t"+pName+"\t  " + amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
