package com.ddone.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;


/**
 * @author ddone
 * @date 2020/6/22-11:05
 */
public class HBase_DML {
    public static void main(String[] args) throws IOException {
        putData("test","1001","info","name","HR");
        putData("test","1002","info","addr","sh");
        putData("test","1003","info","num","10021");
        getData("test","1004","info","addr");
//        deleteData("dept","1001","info1","name");
//            scanTable("dept");
            close();

    }
    private static Connection connection;
    static {
        //0.1.创建配置信息并制定连接的集群
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","hadoop102,hadoop103,hadoop104");
        //① 创建连接器
        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void close() throws IOException {
    }
    //新增和修改数据
    public static void putData(String tableName,String rowKey,String cf,String cn,String value) throws IOException {
        //1.获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        //2.创建put对象
        Put put = new Put(Bytes.toBytes(rowKey));
        //3.添加数据
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn),Bytes.toBytes(value));
        //4.执行添加
        table.put(put);
        //5.释放
        table.close();
        close();
    }
    //单条查询语句
    public static void getData(String  tableName,String rowKey,String cf,String cn) throws IOException {

        //1.获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));//传表的名字
        //2.创建get对象
            //①只指定rowKey
        Get get = new Get(Bytes.toBytes(rowKey));//传rowKey
        //①指定列族
//        get.addFamily(Bytes.toBytes(cf));
        //②指定例
//        get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));
        //3.查询数据
        Result result = table.get(get);
        //4.解析result
        for (Cell cell : result.rawCells()) {
            System.out.println("rowKey : " + Bytes.toString(CellUtil.cloneRow(cell))+",CF : " +
                    Bytes.toString(CellUtil.cloneFamily(cell))+",CN : " + Bytes.toString(CellUtil.cloneQualifier(cell))+
                    ",Value : " +Bytes.toString(CellUtil.cloneValue(cell)) );
        }
        //5.释放连接
        table.close();
    }
    public static void scanTable(String tableName) throws IOException {
        //1.获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        //2.创建Scan对象
        Scan scan = new Scan();
//        scan.withStopRow(Bytes.toBytes("1003"));
        //3.扫描全表
        ResultScanner results = table.getScanner(scan);
        Iterator<Result> iterator = results.iterator();
        //4.解析结果
        while (iterator.hasNext()){
            Result result = iterator.next();
            for (Cell cell : result.rawCells()) {
                System.out.println("rowKey : " + Bytes.toString(CellUtil.cloneRow(cell))+",CF : " +
                        Bytes.toString(CellUtil.cloneFamily(cell))+",CN : " + Bytes.toString(CellUtil.cloneQualifier(cell))+
                        ",Value : " +Bytes.toString(CellUtil.cloneValue(cell)) );
            }
        }
        //5.释放连接
        table.close();
    }

    //删除数据
    public static void deleteData(String tableName,String rowKey,String cf,String cn) throws IOException {
        //1.获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        //2.创建delete对象

        Delete delete = new Delete(Bytes.toBytes(rowKey));
        //(1)指定列族进行删除
//        delete.addFamily(Bytes.toBytes(cf));
        //②指定列和列族进行删除
//        delete.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));
        //③指定列族和列删除,删除多个版本
        delete.addColumns(Bytes.toBytes(cf), Bytes.toBytes(cn));
        //3.执行删除
        table.delete(delete);
        //4.释放资源
        table.close();

    }


}
