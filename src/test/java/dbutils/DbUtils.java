package dbutils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DbUtils {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void createConnection(){
       String dbUrl="jdbc:oracle:thin:@52.90.15.11:1521:xe";
       String dbUsername="hr";
       String dbPassword="hr";

        try{
           connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void destroy(){
        try{
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if (connection!=null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void executeQuery(String query){
        try{
            statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, resultSet.CONCUR_READ_ONLY);
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            resultSet= statement.executeQuery(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static int getRowCount(String query) throws SQLException {
        resultSet.last();
        int rowCount= resultSet.getRow();
        return rowCount;
    }
    /**
     *
     * @param query
     * @return returns query result in a list of lists where outer list represents
     *         collection of rows and inner lists represent a single row
     */
    public static List<List<Object>> getQueryResultList(String query){
        executeQuery(query);
        List<List<Object>> rowList=new ArrayList<>();
        ResultSetMetaData rsm;
        try{
            rsm= resultSet.getMetaData();
            while(resultSet.next()){
                List<Object> row=new ArrayList<>();
                for(int i=1; i<=rsm.getColumnCount(); i++){
                    row.add(resultSet.getString(i));
                }
                rowList.add(row);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        destroy();
        return rowList;

    }

    /**
     *
     * @param query
     * @param column
     * @return list of values of a single column from the result set
     */

    public static List<Object> getColumnData(String query, String column){
        executeQuery(query);
        List<Object> columnList=new ArrayList<>();
        ResultSetMetaData rsm;
        try{
            rsm= resultSet.getMetaData();
            while(resultSet.next()){
                columnList.add(resultSet.getObject(column));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        destroy();
        return columnList;
    }
    /**
     *
     * @param query
     * @return List of columns returned in result set
     */

    public static List<Object> getColumnName(String query){
        executeQuery(query);
        List<Object> columnNames=new ArrayList<>();
        ResultSetMetaData rsm;
        try{
            rsm= resultSet.getMetaData();
            for(int i=1; i<rsm.getColumnCount(); i++){
                columnNames.add(rsm.getColumnName(i));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        destroy();
        return columnNames;
    }
    /**
     *
     * @param query
     * @return returns query result in a list of maps where the list represents
     *         collection of rows and a map represents represent a single row with
     *         key being the column name
     */

    public static List<Map<String , Object  >> getQueryResultMap(String query){
        executeQuery(query);
        List<Map<String,Object>> rowList=new ArrayList<>();
        ResultSetMetaData rsm;
        try{
            rsm= resultSet.getMetaData();
            while(resultSet.next()){
                Map<String, Object> rowMap=new HashMap<>();
                for (int i=1; i<=rsm.getColumnCount(); i++){
                    rowMap.put(rsm.getColumnName(i),resultSet.getObject(i));
                }
                rowList.add(rowMap);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        destroy();
        return rowList;
    }

    /**
     *
     * @param query
     * @return returns a single cell value. If the results in multiple rows and/or
     *         columns of data, only first column of the first row will be returned.
     *         The rest of the data will be ignored
     */
    public static Object getCellValue(String query){
        return getQueryResultList(query).get(0).get(0);
    }



    public static String costumeDate( String datePart, int value) {
        var c = Calendar.getInstance();
        if (value != 0) {
            switch (datePart) {
                case "DAY":
                    c.add(Calendar.DATE, value);
                    break;
                case "YEAR":
                    c.add(Calendar.YEAR, value);
                    break;
                case "MONTH":
                    c.add(Calendar.MONTH, value);
                    break;
            }
        }
        var dateFormat = new SimpleDateFormat("MM.dd.yyyy");
        return dateFormat.format(c.getTime());
    }

}
