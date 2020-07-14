package com.ddone.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddone
 * @date 2020/7/2-23:28
 */
//求字符串的长度,返回的是int,输入一个字符串,返回一个int的数
public class StrSizeUDF extends GenericUDF {
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        //initialize
        //①判断参数个数
        if(arguments.length != 1){
           throw new UDFArgumentException("函数只能传一个");
        }
        //2.判断参数类型
        if(!"string".equals(arguments[0].getTypeName())){
            throw new UDFArgumentException("函数类型必须是string");
        }
        //3.设定列名和列的类型 返回的是一个Inspector
//        ObjectInspectorFactory.getStandardListObjectInspector();//只有一个参数,也不能返回列的名称
//        PrimitiveObjectInspectorFactory.javaStringObjectInspector;只能返回类型
        List<String> fieldName=new ArrayList<>();
        //列名
        fieldName.add("测试用");
        //添加列名
        List<ObjectInspector> fieldOIs=new ArrayList<>();
        //列的类型
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
        //添加列的类型
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldName,fieldOIs);
        //返回ObjectInspector
    }

    //逻辑代码
    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        if(arguments[0].get() == null){
           return 0;
        }
        return arguments[0].get().toString().length();
    }

    //Get the String to be displayed in explain.
    @Override
    public String getDisplayString(String[] children) {
        return getStandardDisplayString("StrSizeUDF",children);
    }
}
