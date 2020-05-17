/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Event;
import Entities.User;
import Utils.DatabaseConnection;
import Utils.SingletonNavigation;
import java.sql.Connection;
import java.sql.Date;
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
public class EventService {
    Connection cnx = DatabaseConnection.getInstance().getConnection();
    // Service Ajout évènement
    public void insert(Event e) 
    {
        try
        {
            String query = "INSERT INTO event (eventName, eventDetails, eventDate, eventDuration, eventPlace, cover, club_id) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, e.getName());
            statement.setString(2, e.getDescription());
            statement.setDate(3, (Date) e.getDate());
            statement.setInt(4, e.getDuration());
            statement.setString(5, e.getPlace());
            statement.setString(6, e.getCover());
            statement.setInt(7, e.getClub_id());
            statement.executeUpdate();
            System.out.println("evenement crée");
        }catch(SQLException ex)
        {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Service affichage évènement ( pour un club )
    public List<Event> getAll(int club_id) 
    {
        List<Event> eventList = new ArrayList();
        try{
            String query = "SELECT * FROM event WHERE club_id = '" + club_id + "' ORDER BY eventDate DESC";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("eventName"));
                e.setDescription(rs.getString("eventDetails"));
                e.setDate(rs.getDate("eventDate"));
                e.setDuration(rs.getInt("eventDuration"));
                e.setPlace(rs.getString("eventPlace"));
                e.setCover(rs.getString("cover"));
                e.setClub_id(rs.getInt("club_id"));
                eventList.add(e);
            }
        }catch(SQLException ex)
        {
               Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eventList;
    }    
    

    // Service modification d'un évènement
    public void update(Event e) 
    {
        try{
         String query = "UPDATE event SET eventName = ?, eventDetails = ?, eventDate = ?, eventDuration = ?, eventPlace = ?, cover = ? WHERE id=?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, e.getName());
            statement.setString(2, e.getDescription());
            statement.setDate(3, (Date) e.getDate());
            statement.setInt(4, e.getDuration());
            statement.setString(5, e.getPlace());
            statement.setString(6, e.getCover());
            statement.setInt(7, e.getId());
            statement.executeUpdate();
            System.out.println("evenement modifié");
        }catch(SQLException ex)
        {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // Service supression d'un évènement
    public void delete(Event e) 
    {
        try
        {
            String query="DELETE FROM event WHERE id=?";
            PreparedStatement st=cnx.prepareStatement(query);
            st.setInt(1, e.getId());
            st.executeUpdate();
        }catch(SQLException ex) 
        {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void participer(Event e) 
    {
        try
        {
            String query = "INSERT INTO event_user (event_id, user_id) VALUES (?,?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, e.getId());
            statement.setInt(2, SingletonNavigation.getInstance().getLoggedInUser().getId());
            statement.executeUpdate();
            System.out.println("participation ajoutée");
        }catch(SQLException ex)
        {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void annulerParticipation(Event e) 
    {
        try
        {
            String query = "DELETE FROM event_user WHERE event_id = ? AND user_id = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, e.getId());
            statement.setInt(2, SingletonNavigation.getInstance().getLoggedInUser().getId());
            statement.executeUpdate();
            System.out.println("participation annulée");
        }catch(SQLException ex)
        {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      public boolean estParticipants(Event e) 
    {
        int count = 0;
        try{
            String query = "SELECT * FROM event_user WHERE event_id = '" + e.getId() + "' AND user_id = '" + SingletonNavigation.getInstance().getLoggedInUser().getId()+ "'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                count ++;
            }
        }catch(SQLException ex)
        {
               Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count != 0;
    }    
    
    public int getNbrParticipants(Event e) 
    {
        int count = 0;
        try{
            String query = "SELECT * FROM event_user WHERE event_id = '" + e.getId() + "'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                count ++;
            }
        }catch(SQLException ex)
        {
               Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }    
    
    public List<User> getListPart(Event e) 
    {
        int count = 0;
        List<User> partList = new ArrayList<User>();
        UserService us = new UserService();
        try{
            String query = "SELECT * FROM event_user WHERE event_id = '" + e.getId() + "'";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                partList.add(us.getUserById(rs.getInt("user_id")));
            }
        }catch(SQLException ex)
        {
               Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partList;
    }    
      
    public List<Event> getEvents() 
    {
        List<Event> eventList = new ArrayList();
        try{
            String query = "SELECT * FROM event";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
            {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("eventName"));
                e.setDescription(rs.getString("eventDetails"));
                e.setDate(rs.getDate("eventDate"));
                e.setDuration(rs.getInt("eventDuration"));
                e.setPlace(rs.getString("eventPlace"));
                e.setCover(rs.getString("cover"));
                e.setClub_id(rs.getInt("club_id"));
                eventList.add(e);
            }
        }catch(SQLException ex)
        {
               Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eventList;
    }    
}
