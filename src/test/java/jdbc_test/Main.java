package jdbc_test;

import dbutils.DbUtils;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        String dbURL= "jdbc:oracle:thin:@52.90.15.11:1521:xe";
        String dbUsername="hr";
        String dbPassword = "hr";


        Connection connection= DriverManager.getConnection(dbURL,dbUsername, dbPassword);
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select first_name, last_name, salary from employees");

//        List<String> list=new ArrayList<>();
//        while (resultSet.next())  {
//            //System.out.println(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getInt(3));
//            list.add(resultSet.getString(1)+" "+resultSet.getString(2)+" "+resultSet.getInt(3));
//        }


        ResultSetMetaData rsData= resultSet.getMetaData();
        List<Map<String, Object>> tableList=new ArrayList<>();
        resultSet.afterLast();
        System.out.println(resultSet.getRow());

//        while(resultSet.next()){
//            Map<String,Object> lista=new HashMap<>();
//            for (int i=1; i<=rsData.getColumnCount(); i++){
//                lista.put(rsData.getColumnName(i),resultSet.getString(rsData.getColumnName(i)));
//            }
//            tableList.add(lista);
//        }
//
//        for(Map<String, Object> eachRow:tableList){
//            System.out.println(eachRow.toString());
//        }


        connection.close();
        resultSet.close();
        statement.close();



    }
    @Test
    public void test1(){
        DbUtils.createConnection();
        System.out.println(DbUtils.getQueryResultMap("select * from employees"));
        DbUtils.destroy();

    }
}
