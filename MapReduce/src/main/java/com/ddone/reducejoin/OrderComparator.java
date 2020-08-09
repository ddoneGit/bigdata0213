package com.ddone.reducejoin;

import com.ddone.comparator1.OrderInfo1;
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
        OrderBean o1 = (OrderBean) a;
        OrderBean o2 = (OrderBean) b;
        return o1.getPid().compareTo(o2.getPid());
    }

    protected OrderComparator() {
        super(OrderBean.class,true);
    }
}
