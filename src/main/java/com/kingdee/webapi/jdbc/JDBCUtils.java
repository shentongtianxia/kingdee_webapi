package com.kingdee.webapi.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 功能描述：操作数据库的工具类
 * @author leo-zu
 * @date 2022-01-10
 */
@Component
public class JDBCUtils {
    private static JdbcTemplate jdbcTemplate;

    /**
     * 功能描述：获取数据库连接
     * @return 连接
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        // 设置TLS协议版本，允许TLS 1.0
        System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");
        
        // 1、加载配置文件
        InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pro = new Properties();
        pro.load(inputStream);
        String driverClass = pro.getProperty("driverClass");
        String url = pro.getProperty("url");
        String user = pro.getProperty("user");
        String password = pro.getProperty("password");
        // 2、加载驱动
        Class.forName(driverClass);

        // 3、获取连接
        return DriverManager.getConnection(url, user, password);
    }
    
    public static JdbcTemplate getJdbcTemplate() throws IOException {
        // 设置TLS协议版本，允许TLS 1.0
        System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");
        
        InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pro = new Properties();
        pro.load(inputStream);
        String driver = pro.getProperty("driverClass");
        String url = pro.getProperty("url");//连接地址
        String user = pro.getProperty("user");//用户
        String password = pro.getProperty("password");//密码

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    /**
     * 功能描述：关闭资源
     * @param conn
     * @param ps
     */
    public static void closeResource(Connection conn, Statement ps){
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：关闭资源
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs){
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询单条记录
    public <T> T getInstance(Class<T> clazz, String sql, Object... objects){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            connection = JDBCUtils.getConnection();

            ps = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                ps.setObject(i+1, objects[i]);
            }
            rs = ps.executeQuery();
            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if(rs.next()){
                // 获取实例对象
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columnValue = rs.getObject(i + 1);
                    // 获取列的列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, ps, rs);
        }
        return null;
    }
    
    //查询多条记录
    public <T> List<T> queryList(Class<T> clazz, String sql, Object... args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            // 1、获取连接
            connection = JDBCUtils.getConnection();
            // 预编译
            ps = connection.prepareStatement(sql);
            if (args != null){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }

            // 执行sql，获取结果集
            rs = ps.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 获取元数据的列数
            int columnCount = rsmd.getColumnCount();
            // 返回结果
            List<T> resultList = new ArrayList<>();
            while(rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object object = rs.getObject(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, object);
                }
                resultList.add(t);
            }
            return resultList;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, ps, rs);
        }
        return null;
    }
}