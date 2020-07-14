package com.ddone.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddone
 * @date 2020/7/8-22:50
 */
public class SplitUDTF extends GenericUDTF {
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //设定输出的列类型和名字
        //1.判断长度是否为2
        if(argOIs.getAllStructFieldRefs().size() != 2){
            throw new UDFArgumentException("参数的个数必须是2");
        }
        //2.判断类型
        if (!"string".equals(argOIs.getAllStructFieldRefs().get(0).getFieldObjectInspector().getTypeName())) {
            throw new UDFArgumentException("第一个参数类型必须是string");
        }
        if (!"string".equals(argOIs.getAllStructFieldRefs().get(1).getFieldObjectInspector().getTypeName())) {
            throw new UDFArgumentException("第二参数类型必须是string");
        }
        List<String> fieldsName = new ArrayList<>();
        List<ObjectInspector> fieldOIs=new ArrayList<>();
        fieldsName.add("words");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
       return  ObjectInspectorFactory.getStandardStructObjectInspector(fieldsName, fieldOIs);
    }

    ArrayList<String> resultList = new ArrayList<>();
    //forward写出的是一个object
    @Override
    public void process(Object[] args) throws HiveException {

        //1.获取输入数据,并处理
        String str = args[0].toString();
        String splitKey = args[1].toString();
        String[] splits = str.split(splitKey);

        //2.变量集合并写出
        for (String split : splits) {
            resultList.clear();
            resultList.add(split);
            forward(resultList);
        }

    }

    @Override
    public void close() throws HiveException {

    }
}
