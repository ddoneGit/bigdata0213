package com.ddone;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.management.Query;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ddone
 * @date 2020/7/30-00:55
 */
public class DruidJDBCTest {
    public static void main(String[] args) throws Exception {
        Connection connection = getConnectionByDruid();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into mytable values(?,?,?) on duplicate key update age=?");
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2,"wx");
        preparedStatement.setInt(3,40);
        preparedStatement.setInt(4,12);
        preparedStatement.executeUpdate();
//        preparedStatement.close();
//        connection.close();
//        System.out.println(getConnectionByDuid01());
    }

    private static Connection getConnectionByDruid01() throws Exception {
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        return connection;

    }

    public static Connection getConnectionByDruid() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost/ddone");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("abc123");
        druidDataSource.setInitialSize(8);
        druidDataSource.setMaxActive(200);
        DruidPooledConnection connection = druidDataSource.getConnection();
       return connection;
    }
}
