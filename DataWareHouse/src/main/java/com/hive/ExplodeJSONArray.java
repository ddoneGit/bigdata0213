package com.hive;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * @author ddone
 * @date 2020/6/28-08:54
 */
public class ExplodeJSONArray  extends GenericUDTF {
    //1.继承Generic,实现两个方法,
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        if(argOIs.getAllStructFieldRefs().size() !=1){
            throw new UDFArgumentException("explode_json_array 只需要一个参数");
        }
        if(!"string".equals(argOIs.getAllStructFieldRefs().get(0)
                .getFieldObjectInspector().getTypeName())){
            throw new UDFArgumentException("explode_json_array 的第一个参数应为string类型");
        }
        ArrayList<String> filedName = new ArrayList<>();
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<>();
        filedName.add("action");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(filedName, fieldOIs);
        //必须要返回一个StructObjectInspector,里面添加列的名称和类型
    }

    @Override
    public void process(Object[] objects) throws HiveException {
        //实现业务逻辑
        //1.取第一个
        String jsonArray = objects[0].toString();
        JSONArray actions = new JSONArray(jsonArray);
        //2.包装成jsonArray
        for (int i = 0; i < actions.length(); i++) {
            String[] result = new String[1];
            result[0] = actions.getString(i);
            //写出result
            forward(result);
        }

    }

    @Override
    public void close() throws HiveException {

    }
}
