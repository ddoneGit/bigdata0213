package com.ddone.comparator;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/6-17:23
 */
public class OrderInfo implements WritableComparable<OrderInfo>{
    private String order_id;
    private String spu_id;
    private Double amount;

    public OrderInfo() {
    }

    public OrderInfo(String order_id, String spu_id, Double amount) {
        this.order_id = order_id;
        this.spu_id = spu_id;
        this.amount = amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSpu_id() {
        return spu_id;
    }

    public void setSpu_id(String spu_id) {
        this.spu_id = spu_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }




    public void write(DataOutput out) throws IOException {
        out.writeUTF(order_id);
        out.writeUTF(spu_id);
        out.writeDouble(amount);

    }

    public void readFields(DataInput in) throws IOException {
        this.order_id = in.readUTF();
        this.spu_id = in.readUTF();
        this.amount = in.readDouble();
    }

    public int compareTo(OrderInfo o) {
        return 0;
    }
}
