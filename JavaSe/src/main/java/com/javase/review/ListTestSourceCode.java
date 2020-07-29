package com.javase.review;

import java.util.*;

/**
 * @author ddone
 * @date 2020/7/23-22:01
 */
public class ListTestSourceCode {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();//底层是一个Object[]//默认长度是10
        list.add("你好");
        list.add(null);
        System.out.println(list.size());
//        list.remove("kk");
        System.out.println(list.set(1, "该表"));
        System.out.println(list);

//        Vector v = new Vector();
//        v.add("你好");

        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("ni");
        linkedList.remove("你");
        HashSet<String> set = new HashSet<>();
        set.add("xx");
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("你好", "我好");

    }
}
