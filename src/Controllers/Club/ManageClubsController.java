/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Club;

import Entities.Club;
import Entities.User;
import Services.ClubService;
import Services.UserService;
import Utils.Misc;
import com.teknikindustries.bulksms.SMS;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kinto
 */
public class ManageClubsController implements Initializable {

    private Club selectedClub;
    private User selectedUser;
    
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane PaneMods;
    @FXML
    private AnchorPane moderators;
    @FXML
    private AnchorPane PaneMods1;
    @FXML
    private AnchorPane clubs;
    @FXML
    private Label labelhint;
    @FXML
    private Label selectedmodlabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showClubs();
        showModerators();
        PaneMods.setVisible(false);
        labelhint.setVisible(false);
        selectedmodlabel.setVisible(false);
        // TODO
    }    

    @FXML
    private void setModerator(ActionEvent event) {
        PaneMods.setVisible(false);
        labelhint.setVisible(false);
        selectedmodlabel.setVisible(false);
        ClubService cs = new ClubService();
        cs.setModerator(selectedClub.getId(), selectedUser.getId());
        // Notification par sms : 
        SMS sendtext = new SMS();
        sendtext.SendSMS("slimslof","Slimslof123","You have been promoted to the role of the moderator of the " + selectedClub.getName(),"216"+"55409700","https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");
        Misc.showTrayNotification("Success", "The new moderator has been set");
    }
    
    public void showClubs()
    {
        if(clubs.getChildren()!=null)
        {
            clubs.getChildren().clear();
        }
        
        // get Elements to display 
        ClubService cs = new ClubService();
        List<Club> myList=new ArrayList<Club>();
        myList = cs.getAll();
        VBox Container = new VBox();  
        
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(240, 330);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(800);
        Container.setPrefHeight(400);
        
        clubs.setRightAnchor(scrollPane, 0.);
        clubs.setBottomAnchor(scrollPane, 0.);
        clubs.setLeftAnchor(scrollPane, 0.);
            
        for(Club c : myList)
        {
           HBox Hb = new HBox(); 
           Label nom = new Label(" " + c.getName());
           nom.setMinWidth(180);
           nom.setMinHeight(10);
           nom.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 22; -fx-font-family: 'Oswald Regular'");
           nom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
              PaneMods.setVisible(true);
              selectedClub = c;
              labelhint.setText("Setting moderator for " + c.getName());
              labelhint.setVisible(true);
            }
          });
           Hb.getChildren().add(nom);
           Container.getChildren().add(Hb);
      
        }
        clubs.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background :   #DDEBE9; -fx-border-color: #ffffff");

    }
    
    public void showModerators()
    {
        if(moderators.getChildren()!=null)
        {
            moderators.getChildren().clear();
        }
        
        // get Elements to display 
        UserService us = new UserService();
        List<User> myList=new ArrayList<User>();
        myList = us.getAll();
        VBox Container = new VBox();  
        
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(240, 330);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(800);
        Container.setPrefHeight(400);
        
        moderators.setRightAnchor(scrollPane, 0.);
        moderators.setBottomAnchor(scrollPane, 0.);
        moderators.setLeftAnchor(scrollPane, 0.);
            
        for(User u : myList)
        {
           HBox Hb = new HBox(); 
           Label nom = new Label(" " + u.getUsername());
           nom.setMinWidth(180);
           nom.setMinHeight(10);
           nom.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 22; -fx-font-family: 'Oswald Regular'");
           nom.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
              selectedUser = u;
              selectedmodlabel.setText("Selected moderator : " + u.getUsername());
              selectedmodlabel.setVisible(true);
                
                
            }
          });
           Hb.getChildren().add(nom);
           Container.getChildren().add(Hb);
        }
        moderators.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background :   #DDEBE9; -fx-border-color: #ffffff");

    }
}
