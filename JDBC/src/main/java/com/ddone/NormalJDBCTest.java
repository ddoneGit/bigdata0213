package com.ddone;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author ddone
 * @date 2020/7/25-20:54
 */
public class NormalJDBCTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Properties pro = new Properties();
        pro.load(ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties"));
        //0.加载驱动
        Class.forName(pro.getProperty("driverClass"));//加载驱动
        //1.获取配置信息
        String url = pro.getProperty("url");
        String user = pro.getProperty("user");
        String password = pro.getProperty("password");
        //2.创建连接
        Connection connection = DriverManager.getConnection(url, user, password);
        getQuery(connection);
//        getInsert(connection);
        getDelete(connection);
        getUpdate(connection);
        connection.close();
    }

    private static void getUpdate(Connection connection) throws SQLException {
        String sql = "update test set name='Update' where id = 1";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.executeUpdate();
    }

    private static void getDelete(Connection connection) throws SQLException {
        String sql = "delete from test where id = ?";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setInt(1,2);
        pre.executeUpdate();
    }

    private static void getInsert(Connection connection) throws SQLException {
        String sql = "insert into test values(?,?)";
        String tableName = "test";
        int id = 3;
        String name = "ccc";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1,tableName);
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,name);
        preparedStatement.executeUpdate();

    }

    public static void getQuery(Connection connection) throws SQLException {
        //3.查询语句
        PreparedStatement preparedStatement = connection.prepareStatement("select * from test");
        //4.查询结果
        ResultSet resultSet = preparedStatement.executeQuery();//executeUpdate
        //5.解析结果
        while (resultSet.next()){
            System.out.println("id: "+resultSet.getInt(1)+" name: "+resultSet.getString(2));
        }
        //关闭连接
    }
}
