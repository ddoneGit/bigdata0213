package com.hive;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ConstantObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddone
 * @date 2020/6/29-18:13
 */
public class JsonArrayToStructArray extends GenericUDF {
    /**
     * 确定列名和列的类型
     *
     * @param
     * @return
     * @throws UDFArgumentException 参数个数至少为3个,并且必须是string
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arg) throws UDFArgumentException {
        /**
         *
         * 参数类型
         * ("[{},{}]",a1,b1,c1,a1:string,b1:string,c1:string)
         *
         */
        if (arg.length < 3) {
            throw new UDFArgumentException("参数个数必须是三个");
        }
        for (int i = 0; i < arg.length; i++) {
            if (!"string".equals(arg[i].getTypeName())) {
                throw new UDFArgumentException("参数类型需要时string");
            }
        }
        //添加列名和列的类型
        List<String> fieldNames = new ArrayList<>();
        //添加列的类型
        List<ObjectInspector> fieldOIs = new ArrayList<>();

        //取出包含参数和列的一部分进行遍历和添加
        for (int i = 1 + (arg.length - 1) / 2; i < arg.length; i++) {
            if (!(arg[i] instanceof ConstantObjectInspector)) {
                throw new UDFArgumentException("json_array_to_struct_array第" + i + "个参数应该string类型");
            }
            String filed = ((ConstantObjectInspector) arg[i]).getWritableConstantValue().toString();
            String[] splits = filed.split(":");
            fieldNames.add(splits[0]);
            switch (splits[1]) {
                case "string":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
                    break;
                case "boolean":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaBooleanObjectInspector);
                    break;
                case "tinyint":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaByteObjectInspector);
                    break;
                case "smallint":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaShortObjectInspector);
                    break;
                case "int":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
                    break;
                case "bigint":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaLongObjectInspector);
                    break;
                case "float":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaFloatObjectInspector);
                    break;
                case "double":
                    fieldOIs.add(PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
                    break;
                default:
                    throw new UDFArgumentException("json_array_to_struct_array 不支持" + splits[1] + "类型");
            }

        }
        //返回输出的类型 List(List())
        return ObjectInspectorFactory.getStandardListObjectInspector(
                ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs)
        );
    }

    /**
     * 执行计算
     *
     * @param
     * @return
     * @throws HiveException
     */
    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        if(arguments[0].get() == null){
            return null;
        }
        ArrayList<List<Object>> result = new ArrayList<>();
        String line = arguments[0].get().toString();
        //1.取出需要处理的数据,并且包装成json
        JSONArray jsonArray = new JSONArray(line);
        //遍历json数组
        for (int i = 0; i <jsonArray.length(); i++) {
            ArrayList<Object> struct= new ArrayList<>();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (int j= 1; j < 1+(arguments.length-1)/2; j++) {
                if(jsonObject.has(arguments[j].get().toString())){
                    //获取json中的value
                   struct.add(jsonObject.get(arguments[j].get().toString()));
                }else {
                    struct.add(null);
                }
                //用于存放内部数据
            }
            result.add(struct);
        }
        return result;
    }
    /**
     * 执行explain计划
     *
     * @param
     * @return
     */
    @Override
    public String getDisplayString(String[] children) {
        return getStandardDisplayString("json_array_to_struct_array", children);
    }
}
