/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.DriverManager;
import java.sql.Connection;

/**
 *
 * @author Antsa
 */
public class Connexion {
    public static Connection getConnect(String base) throws Exception{
        try{
            if(base.equalsIgnoreCase("oracle"))
            {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","banque","banque");
                return con;
            }
            else{
                Class.forName("org.postgresql.Driver");
                Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/stock", "stock", "stock");
                return connect;    
            }
        }
        catch(Exception e){
            throw e;
        }
    }
}  
