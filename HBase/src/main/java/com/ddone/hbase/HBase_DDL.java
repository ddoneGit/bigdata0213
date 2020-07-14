package com.ddone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceExistException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/6/22-11:04
 */
public class HBase_DDL {
    public static void main(String[] args) throws IOException {
//        createNS("bigdata");
//        System.out.println(isTableExists("ddone:emp"));
        createTable("test","info");
//        dropTable("test11");
//        close();

    }
    //创建命名空间

    private static Connection connection;
    private  static Admin admin;
    //初始化对象信息
    static {
        //0.1.创建配置信息并制定连接的集群
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","hadoop102,hadoop103,hadoop104");
        //① 创建连接器
        try {
            connection = ConnectionFactory.createConnection(configuration);
            //②创建DDL操作对象
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建命名空间
     * @param ns 命名空间名字
     * @throws IOException
     */
    public static void createNS(String ns) throws IOException {
        //③创建命名空间
        // 3,1创建命名空间描述器
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(ns).build();
        try {
            admin.createNamespace(namespaceDescriptor);
        } catch (NamespaceExistException e) {
            System.out.println("命名空间已经存在");
//            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        //3.2释放连接
        close();
    }

    /**
     * 释放连接
     * @throws IOException
     */
    public static void close() throws IOException {
        connection.close();
        admin.close();
    }

    /**
     * 判断表是否存在
     * @param tableName
     * @return
     * @throws IOException
     */
    public static Boolean isTableExists(String tableName) throws IOException {
        return admin.tableExists(TableName.valueOf(tableName));
    }

    /**
     *
     * @param tableName 要创建表的名字
     * @param cfs 创建表的列族信息
     */
    public static void createTable(String tableName,String... cfs) throws IOException {
        //1.判断列族信息是否少于0个
        if(cfs.length <= 0){
            System.out.println("列族少于1...");
            return;
        }
        //2.判断表是否存在
        if(isTableExists(tableName)){
            System.out.println("该表已经存在哦");
            return;
        }
        //3.创建表
            //1.创建表描述器Build对象
        TableDescriptorBuilder tableDescriptorBuilder =
                TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));
        tableDescriptorBuilder.setCoprocessor("com.ddone.hbase.HBase_Coprocessor");
            //2.循环放入列族信息 建表 create 'stu33','info1','info2'
        for (String cf:cfs) {
            //①创建列族描述器Builder
            ColumnFamilyDescriptor columnFamilyDescriptor =
                    ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf)).build();
            tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptor);

        }
        TableDescriptor descriptor = tableDescriptorBuilder.build();
        admin.createTable(descriptor);//需要描述器
    }
    public static void dropTable(String tableName) throws IOException {
        if(!isTableExists(tableName)){
            System.out.println("该表不存在哦");
            return;
        }
        //①使表不可用
        admin.disableTable(TableName.valueOf(tableName));
        //②删除表
        admin.deleteTable(TableName.valueOf(tableName));

    }
}
