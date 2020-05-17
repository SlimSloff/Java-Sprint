/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class DatabaseConnection 
{
    String url = "jdbc:mysql://localhost:3306/ecoledb";
    String login="root";
    String mdp="";
    Connection connection;
    static DatabaseConnection instance;
    
    private DatabaseConnection()
    {
        try
        {
            connection = DriverManager.getConnection(url,login,mdp);
            System.out.println("Connexion établi");
        }
        catch(SQLException exception)
        {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
    
    public Connection getConnection(){return connection;}
    
    public static DatabaseConnection getInstance()
    {
        if(instance == null)
        {
            instance = new DatabaseConnection();
            return instance;
        }
        else
            return instance;
    }
}
