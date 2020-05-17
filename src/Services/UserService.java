/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.User;
import Utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class UserService 
{
    Connection cnx = DatabaseConnection.getInstance().getConnection();
    
    public User getUserByUsername(String username) {
        User u = new User();
        try{
         String query = "SELECT * FROM fos_user WHERE username = "+"'"+username+"'";
         Statement statement = cnx.createStatement();
         ResultSet rs = statement.executeQuery(query);
         while(rs.next())
         {
             u.setId(rs.getInt(1));
             u.setUsername(rs.getString("username"));
             u.setUsernameCanonical(rs.getString("username_canonical"));
             u.setEmail(rs.getString("email"));
             u.setEmailCanonical(rs.getString("email_canonical"));
             u.setEnabled(rs.getBoolean("enabled"));
             u.setSalt(rs.getString("salt"));
             u.setPassword(rs.getString("password"));             
         }
        }catch(SQLException ex)
        {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    
    public User getUserById(int id) {
        User u = new User();
        try{
         String query = "SELECT * FROM fos_user WHERE id = "+"'"+id+"'";
         Statement statement = cnx.createStatement();
         ResultSet rs = statement.executeQuery(query);
         while(rs.next())
         {
             u.setId(rs.getInt(1));
             u.setUsername(rs.getString("username"));
             u.setUsernameCanonical(rs.getString("username_canonical"));
             u.setEmail(rs.getString("email"));
             u.setEmailCanonical(rs.getString("email_canonical"));
             u.setEnabled(rs.getBoolean("enabled"));
             u.setSalt(rs.getString("salt"));
             u.setPassword(rs.getString("password"));             
         }
        }catch(SQLException ex)
        {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    
     
    public List<User> getAll() {
        List<User> userList = new ArrayList<User>();
        try{
         String query = "SELECT * FROM fos_user";
         Statement statement = cnx.createStatement();
         ResultSet rs = statement.executeQuery(query);
         while(rs.next())
         {
             User u = new User(); 
             u.setId(rs.getInt(1));
             u.setUsername(rs.getString("username"));
             u.setUsernameCanonical(rs.getString("username_canonical"));
             u.setEmail(rs.getString("email"));
             u.setEmailCanonical(rs.getString("email_canonical"));
             u.setEnabled(rs.getBoolean("enabled"));
             u.setSalt(rs.getString("salt"));
             u.setPassword(rs.getString("password"));   
             userList.add(u);
         }
        }catch(SQLException ex)
        {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }
}
