package com.ddone.kylin;

import java.sql.*;

/**
 * @author ddone
 * @date 2020/7/8-11:21
 */
public class KylinTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //Kylin_JDBC 驱动
        String kylin_Driver = "org.apache.kylin.jdbc.Driver";
        //Kylin_URL
        String kylin_URL = "jdbc:kylin://hadoop102:7070/First";
        //Kylin的用户名
        String kylin_User = "ADMIN";
        //Kylin的密码
        String kylin_Password = "KYLIN";
        //添加驱动信息
        Class.forName(kylin_Driver);
        Connection connection = DriverManager.getConnection(kylin_URL, kylin_User, kylin_Password);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from dwd_dim_base_province");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println("ID: " + resultSet.getString(1) + ",ISO_CODE: " + resultSet.getString(2)
                    + ",REGION_ID: " + resultSet.getString(3));
        }
        //释放连接
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

}
