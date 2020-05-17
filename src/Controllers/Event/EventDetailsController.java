/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Event;

import Entities.Event;
import Entities.User;
import Services.EventService;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author kinto
 */
public class EventDetailsController implements Initializable {

    private Event event;
    @FXML
    private ImageView cover;
    @FXML
    private Label name;
    @FXML
    private TextArea description;
    @FXML
    private Label date;
    @FXML
    private Button join_btn;
    @FXML
    private Label duration;
    @FXML
    private Label place;
    @FXML
    private Label participants;
    @FXML
    private AnchorPane content;
    private EventService es = new EventService();

    public void setEvent(Event event) {
        this.event = event;
    }
    
     public void fillForm() throws Exception
    {
       if(es.estParticipants(event))
       {
           join_btn.setText("Leave event");
       }
       name.setText(event.getName());
       description.setText(event.getDescription());
       place.setText("Place : " + event.getPlace());
       date.setText("Date : " + event.getDate());
       duration.setText("Duration : " + String.valueOf(event.getDuration()) + " Days");
       participants.setText("Participants : (" + String.valueOf(es.getNbrParticipants(event))+")");
       URL l_url = new URL("http://localhost/ecolewamp/"+event.getCover());
       BufferedImage imae = ImageIO.read(l_url);
       Image imge = SwingFXUtils.toFXImage(imae, null);
       this.cover.setImage(imge);
       afficherParticipants();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void joinEvent(ActionEvent ev) throws Exception{
        if(join_btn.getText().equals("Join event"))
        {
            es.participer(event);
            fillForm();
            join_btn.setText("Leave event");
        }
        else
        {
            es.annulerParticipation(event);
            fillForm();
            join_btn.setText("Join event");
        }
    }
    
    public void afficherParticipants()
    {
        // TODO
        if(content.getChildren()!=null)
        {
            content.getChildren().clear();
        }
        
        // get Elements to display 
        
        List<User> myList=new ArrayList<User>();
        myList = es.getListPart(event);
        VBox Container = new VBox();  // main container for all data specific to a service
        
        // Scroll pane to display all the found services
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(800, 400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(294);
        Container.setPrefHeight(59);
        
        content.setRightAnchor(scrollPane, 0.);
        content.setBottomAnchor(scrollPane, 0.);
        content.setLeftAnchor(scrollPane, 0.);
    
        //Container.setPadding(new Insets(30,30,30,30));
        
        // iterate through all events and create an event element
        for(User u : myList)
        {
           HBox Hb = new HBox(); 
           Label nom = new Label(" " + u.getUsername());
           nom.setMinWidth(180);
           nom.setMinHeight(10);
           nom.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 22; -fx-font-family: 'Oswald Regular'");
          
           Hb.getChildren().add(nom);
           Container.getChildren().add(Hb);
      
        }
        // Finally add all the events inside the Scrollpane to the main content Anchorpane
        content.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background :  #DDEBE9; -fx-border-color: #ffffff");

    }
    
}
