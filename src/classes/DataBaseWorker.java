package classes;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by ABoK4Do on 07.12.16.
 */
public class DataBaseWorker {
    private static String dbURL = "jdbc:derby:/Users/ABoK4Do/Documents/DBjsp/Restaurant;create=false";
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String queryShowDB = "Select t1.Name as name, t2.NAME as Cat_name, t1.PRICE price " +
                                              "From APP.FOODS t1 LEFT JOIN APP.CATEGORY t2 " +
                                              "ON t1.CATEGORY_ID = t2.ID";

    private static Connection conn = null;
    private static Statement stmt = null;


    private static void createConnection()
    {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(dbURL);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addOne(Object...args){
        if(conn == null) { createConnection();}
        String name = args[0].toString();
        try {
            stmt = conn.createStatement();
            //Нахожу максимальный айди и потом к нему +1
            ResultSet results = stmt.executeQuery("SELECT MAX(ID) From APP.FOODS");
            results.next();
            int newID = results.getInt(1)+1;
            //Добавляю новую строку
            if(args.length==3){
                results = stmt.executeQuery("SELECT ID From APP.CATEGORY WHERE NAME='"+args[1].toString()+"'");
                results.next();
                int cat_id = results.getInt(1);

                stmt.executeUpdate("INSERT INTO APP.FOODS(ID, NAME, CATEGORY_ID, PRICE) VALUES("+newID+",'"+name+"',"+cat_id+","+(int)args[2]+")");}
            if(args.length==2){
                stmt.executeUpdate("INSERT INTO APP.FOODS(ID, NAME, PRICE) VALUES("+newID+",'"+name+"',"+(int)args[1]+")");
                }
            stmt.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList showDB(){

        ArrayList listTable = new ArrayList();
        listTable.clear();
        ResultSet results=null;
        try {
            if(conn == null) { createConnection();}
            stmt = conn.createStatement();
            results = stmt.executeQuery(queryShowDB);
            ResultSetMetaData rsmd = null;
            //Вывод результата запроса в виде ArrayList, состоящий из ArrayListов
            try {
            rsmd = results.getMetaData();
             } catch (SQLException e) {
                  e.printStackTrace();
               }
            int columns = rsmd.getColumnCount(); //Кол-во заголовков
            ArrayList columnNames = new ArrayList();
            for (int i = 1; i <= columns; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
            listTable.add(columnNames);

            while(results.next()){
                ArrayList row = new ArrayList();
                for (int i = 1; i <= columns; i++) {
                    row.add(results.getString(i));
                }
                listTable.add(row);
            }




           // int columns = rsmd.getColumnCount(); //Кол-во заголовков
           // for (int i = 1; i <= columns; i++) {
           //     System.out.println(rsmd.getColumnName(i));
           // }



        } catch (Exception e) {
            e.printStackTrace();
        }


        return listTable;
    }
    public static void delOne(String name){
        //Проверяю подключение к базе данных
        if(conn == null) { createConnection();}
        try {
            //Удалю строку
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM APP.FOODS WHERE NAME='"+name+"'");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

