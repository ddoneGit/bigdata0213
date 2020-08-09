package com.ddone.comparator1;

import com.ddone.reducejoin.OrderBean;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author ddone
 * @date 2020/8/6-22:52
 */
public class OrderComparator extends WritableComparator {
    /**
     * 自定义比较,只比较Id Id相同就认为是一组Key
     * @param a
     * @param b
     * @return
     */
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderInfo1 o1 = (OrderInfo1) a;
        OrderInfo1 o2 = (OrderInfo1) b;
        return o1.getOrder_id().compareTo(o2.getOrder_id());
    }

    protected  OrderComparator() {
        super(OrderInfo1.class,true);
    }
}
