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

public class postgresqlcon{

    static Connection conn = null;

    private static int person_id;

    public static void start(String f_id) {


        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/attendance", "postgres", "4klabs");


            getPerson(f_id);

            if(person_id==0){

                System.out.println("User not found");


            }else{


                System.out.println(person_id);
                getTimeStamp();

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    static void getPerson(String f_id){
        try {

            PreparedStatement  getPersonStatment = conn.prepareStatement("SELECT person_id FROM person WHERE f_id = (?)");


            getPersonStatment.setString(1, f_id);


            ResultSet rs = getPersonStatment.executeQuery();




            while (rs.next())
            {

                person_id= rs.getInt("person_id");

            }

            getPersonStatment.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void getTimeStamp(){
        try {

            PreparedStatement  getTimestampStatment = conn.prepareStatement("SELECT start_time FROM attendance WHERE person_id = (?) AND start_time IS NOT NULL AND end_time IS NULL");


            getTimestampStatment.setInt(1, person_id);


            ResultSet timestamp = getTimestampStatment.executeQuery();




            int recordCounter=0;

            while (timestamp.next()) {
                recordCounter++;
            }


            if(recordCounter>0){

                System.out.println("Ending timer... ");

                PreparedStatement endTimer = conn.prepareStatement("UPDATE attendance SET end_time = CURRENT_TIMESTAMP WHERE person_id = (?) AND end_time IS NULL");
                endTimer.setInt(1, person_id);

                endTimer.executeUpdate();

                endTimer.close();

            }else{

                System.out.println("Starting timer... ");

                PreparedStatement startTimer = conn.prepareStatement("INSERT INTO attendance (person_id) VALUES (?)");

                startTimer.setInt(1, person_id);

                startTimer.executeUpdate();

                startTimer.close();

            }

            getTimestampStatment.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
