/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Event;
import Entities.User;
import Services.EventService;
import Services.UserService;
import Utils.SendEmail;
import Utils.SingletonNavigation;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class BackController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane content;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Api mail : notification des utilisateurs participants dans des évènement
        EventService es = new EventService();
        UserService us = new UserService();
        List<Event> eventList = es.getEvents();
        List<User> partList = new ArrayList<User>();
        for(Event e : eventList) {
            partList = es.getListPart(e);
            for(User u : partList) {
                SendEmail.Send(u.getEmail(),"Upcomming event notification", "The event " + e.getName() + " is approaching ! date is set to " + e.getDate() + " and it will last " + e.getDuration() + " days");
            }
        }
        
    }    
 
    public void initsidebar() throws Exception
    {
        
    }
   

    @FXML
    private void stats(ActionEvent event)
    {
        try 
        {
            content = FXMLLoader.load(getClass().getResource("/Views/Club/statistiques.fxml"));
            root.getChildren().set(1, content);
            content.setLayoutX(0);
            content.setLayoutY(70);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(BackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @FXML
    private void clubs(ActionEvent event) {
        try 
        {
            content = FXMLLoader.load(getClass().getResource("/Views/Club/ManageClubs.fxml"));
            root.getChildren().set(1, content);
            content.setLayoutX(0);
            content.setLayoutY(70);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(BackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
