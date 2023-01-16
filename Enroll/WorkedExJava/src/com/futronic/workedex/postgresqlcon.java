/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.futronic.workedex;

/**
 *
 * @author 4KLabs
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class postgresqlcon{

    static Connection conn = null;

    

    public static void start() {


        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/attendance", "postgres", "tibebu4k");


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    
     static void addUser(User user){
         
         
         

         
        try {
           PreparedStatement addUser = conn.prepareStatement("INSERT INTO person(f_id, name, fathers_name, department)  VALUES (?,?,?,?)");
            
            addUser.setString(1, user.f_id);
            addUser.setString(2, user.name);
            addUser.setString(3, user.fathers_name);
            addUser.setString(4, user.department);

            addUser.execute();

            addUser.close();
                
        } catch (SQLException ex) {
            
            Logger.getLogger(postgresqlcon.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        System.out.println("User Added");
        
     }

}
