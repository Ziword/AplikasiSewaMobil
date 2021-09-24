/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Starting;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author henry
 */
public class koneksidb {
    
    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pbo_uas?useTimezone=true&serverTimezone=UTC","root","");
        }catch(Exception ex){
            System.out.println("Gagal Koneksi ke Database");
            System.out.println(ex.getMessage());
            
        }
        return con; 
    }
    
        private static Connection con;
        
        public static  Connection koneksi()
    {
        try {
            if(con == null) {
                String db = "jdbc:mysql://localhost/pbo_uas?useTimezone=true&serverTimezone=UTC";
                String user = "root";
                String pass = "";
                con = (Connection)DriverManager.getConnection(db,user,pass);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return con;
    }
}

