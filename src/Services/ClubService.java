/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Club;
import Entities.User;
import Utils.DatabaseConnection;
import Utils.SingletonNavigation;
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
 * @author kinto
 */
public class ClubService {
    Connection cnx = DatabaseConnection.getInstance().getConnection();
    
    public void insert(Club c) 
    {
        try
        {
            String query = "INSERT INTO club (moderator, clubName, clubLogo, clubDescription, clubType ) VALUES (?,?,?,?,?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, c.getModerator());
            statement.setString(2, c.getName());
            statement.setString(3, c.getLogo());
            statement.setString(4, c.getDescription());
            statement.setString(5, c.getType());
            statement.executeUpdate();
            System.out.println("club ajouté");
        }catch(SQLException ex)
        {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public List<Club> getAll() 
    {
        List<Club> clubList = new ArrayList();
        try{
            String query = "SELECT * FROM club";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                Club c = new Club();
                c.setId(rs.getInt("id"));
                c.setModerator(rs.getInt("moderator"));
                c.setName(rs.getString("clubName"));
                c.setDescription(rs.getString("clubDescription"));
                c.setType(rs.getString("clubType"));
                c.setLogo(rs.getString("clubLogo"));
                clubList.add(c);
            }
        }catch(SQLException ex)
        {
               Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clubList;
    }    

    public List<Club> search(String name) 
    {
        List<Club> clubList = new ArrayList();
        try{
            String query = "SELECT * FROM club WHERE clubName LIKE '%"+ name +"%'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                Club c = new Club();
                c.setId(rs.getInt("id"));
                c.setModerator(rs.getInt("moderator"));
                c.setName(rs.getString("clubName"));
                c.setDescription(rs.getString("clubDescription"));
                c.setType(rs.getString("clubType"));
                c.setLogo(rs.getString("clubLogo"));
                clubList.add(c);
            }
        }catch(SQLException ex)
        {
               Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clubList;
    }    
    
    public List<Club> getMine() 
    {
        List<Club> clubList = new ArrayList();
        try{
            String query = "SELECT * FROM club WHERE moderator = '"+ SingletonNavigation.getInstance().getLoggedInUser().getId()+"'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                Club c = new Club();
                c.setId(rs.getInt("id"));
                c.setModerator(rs.getInt("moderator"));
                c.setName(rs.getString("clubName"));
                c.setDescription(rs.getString("clubDescription"));
                c.setType(rs.getString("clubType"));
                c.setLogo(rs.getString("clubLogo"));
                clubList.add(c);

            }
        }catch(SQLException ex)
        {
               Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clubList;
    }    
        
    public void update(Club c) 
    {
        try{
         String query = "UPDATE club SET clubName = ?, clubLogo = ?, clubDescription = ?, clubType = ? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, c.getName());
            statement.setString(2, c.getLogo());
            statement.setString(3, c.getDescription());
            statement.setString(4, c.getType());
            statement.setInt(5, c.getId());
            statement.executeUpdate();
            System.out.println("club modifié");
        }catch(SQLException ex)
        {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(Club c) 
    {
        try
        {
            String query="DELETE FROM club WHERE id=?";
            PreparedStatement st=cnx.prepareStatement(query);
            st.setInt(1, c.getId());
            st.executeUpdate();
        }catch(SQLException ex) 
        {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getMemberShipStatus(int club_id, int user_id)
    {
        boolean status = false;
        try{
            String query = "SELECT * FROM club_member WHERE club_id = '" + club_id + "' AND user_id = '" + user_id + "'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                
                status = true;

            }
        }catch(SQLException ex)
        {
               Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public void setModerator(int club_id, int user_id)
    {
        try{
         String query = "UPDATE club SET moderator = ? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, club_id);
            statement.executeUpdate();
            System.out.println("moderateur modifié");
        }catch(SQLException ex)
        {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getModerator(int club_id)
    {
        String moderator = "";
        UserService us = new UserService();
        try{
            String query = "SELECT * FROM club WHERE id = '" + club_id + "'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                
                moderator = us.getUserById(rs.getInt("moderator")).getUsername();

            }
        }catch(SQLException ex)
        {
               Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moderator;
    }
    
    public void addMember(int club_id, int user_id)
    {
        try
        {
            String query = "INSERT INTO club_member (club_id, user_id) VALUES (?,?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, club_id);
            statement.setInt(2, user_id);
            statement.executeUpdate();
            System.out.println("membre ajouté");
        }catch(SQLException ex)
        {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeMember(int club_id, int user_id)
    {
        try
        {
            String query="DELETE FROM club_member WHERE club_id=? AND user_id = ?";
            PreparedStatement st=cnx.prepareStatement(query);
            st.setInt(1, club_id);
            st.setInt(2, user_id);
            st.executeUpdate();
        }catch(SQLException ex) 
        {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<User> getMemberList(int club_id)
    {
        UserService us = new UserService();
        List<User> memberList = new ArrayList<User>();
        try{
            String query = "SELECT * FROM club_member WHERE club_id = '" + club_id + "'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                
                memberList.add(us.getUserById(rs.getInt("user_id")));

            }
        }catch(SQLException ex)
        {
               Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return memberList;
    }
    
    public int getClubId(String name)
    {
        int id = 0 ;
        try{
            String query = "SELECT * FROM club WHERE clubName = '" + name + "'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                
                id = rs.getInt("id");

            }
        }catch(SQLException ex)
        {
               Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
      public int nbrClubParType (String type){
        int count = 0;
        try{
            String sql="SELECT * FROM club where clubType = '" + type + "'";

            ResultSet rs=cnx.createStatement().executeQuery(sql);
            
            while(rs.next()){
                    count++;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
     
       return count;
        
    }    
}
