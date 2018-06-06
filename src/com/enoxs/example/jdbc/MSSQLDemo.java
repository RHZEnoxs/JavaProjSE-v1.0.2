package com.enoxs.example.jdbc;

import java.sql.*;

public class MSSQLDemo {
    public MSSQLDemo(){
        try {
            /*Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            System.setProperty("jdbc.drivers", "com.microsoft.sqlserver.jdbc.SQLServerDriver");*/
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connUrl = "jdbc:sqlserver://192.168.0.100:1433;databaseName=VIPTempDB";
            Connection conn = null;
            conn = DriverManager.getConnection(connUrl, "sa", "mayasa");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TOP 50 * FROM VIPBDTBL");
            while(rs.next()) {
                System.out.println("id = " + rs.getString("id"));
                System.out.println("data_time = " + rs.getString("data_time"));
            }

           /* String ins_stmt = "INSERT INTO employee VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(ins_stmt);
            pstmt.setInt(1, 1009);
            pstmt.setString(2, "Jean Tsao");
            pstmt.setString(3, "2008/10/10");
            int num = pstmt.executeUpdate();*/
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
       new MSSQLDemo();
    }
}
