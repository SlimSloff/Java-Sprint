/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Club;

import Controllers.Event.CreateEventController;
import Controllers.Event.EventDetailsController;
import Entities.Club;
import Entities.Event;
import Entities.User;
import Services.ClubService;
import Services.EventService;
import Utils.SingletonNavigation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author kinto
 */
public class ClubDetailsController implements Initializable {
    private Club club;
    @FXML
    private Button addEvent_btn;
    @FXML
    private Label libelle;
    @FXML
    private Label type;
    @FXML
    private Label responsable;
    @FXML
    private TextArea description;
    @FXML
    private AnchorPane content;
    @FXML
    private ImageView image;
    @FXML
    private AnchorPane parent;
    @FXML
    private Label nomResponsable;
    @FXML
    private Button join_btn;
    @FXML
    private AnchorPane members;
    @FXML
    private Label nbmembers;
    
    public void setClub(Club club) {
        this.club = club;
    }
    
    public void fillForm() throws Exception
    {
       ClubService cs = new ClubService();
       libelle.setText(club.getName());
       description.setText(club.getDescription());
       type.setText("Activity : " + club.getType());
       URL l_url = new URL("http://localhost/ecolewamp/"+club.getLogo());
       BufferedImage imae = ImageIO.read(l_url);
       Image imge = SwingFXUtils.toFXImage(imae, null);
       this.image.setImage(imge);
       if(club.getModerator()== SingletonNavigation.getInstance().getLoggedInUser().getId())
       {
           responsable.setText("You are the moderator");
           addEvent_btn.setVisible(true);
           nomResponsable.setVisible(false);
           join_btn.setVisible(false);

       }
       else{
           addEvent_btn.setVisible(false);
           nomResponsable.setText(cs.getModerator(club.getId()));
           nomResponsable.setVisible(true);

       }
       if(cs.getMemberShipStatus(club.getId(), SingletonNavigation.getInstance().getLoggedInUser().getId()))
           join_btn.setText("Leave club");
       
       afficherMembres();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void createEvent(ActionEvent event) throws Exception{
        
        
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Event/CreateEvent.fxml"));
        try {
            content = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        parent.getChildren().set(0, content);
        content.setLayoutX(20);
        content.setLayoutY(137);
        CreateEventController sec = loader.getController();
        sec.setClub_id(club.getId());
    }
    
    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
    
    public void ShowAll() throws Exception
    {
        // clear existing Content if it exists  
        if(content.getChildren()!=null)
        {
            content.getChildren().clear();
        }
        // get Elements to display 
        EventService es=new EventService();
        List<Event> myList=new ArrayList<Event>();
        myList = es.getAll(club.getId());
        VBox Container = new VBox();  // main container for all data specific to an event
        
        // Scroll pane to display all the found events
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(840, 340);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(840);
        Container.setPrefHeight(340);
        content.setRightAnchor(scrollPane, 0.);
        content.setBottomAnchor(scrollPane, 0.);
        content.setLeftAnchor(scrollPane, 0.);
    
        Container.setPadding(new Insets(15,15,15,15));
        // iterate through all events and create an event element
        for(Event e : myList)
        {
            // Event Cover photo : first element
            URL l_url = new URL("http://localhost/ecolewamp/"+e.getCover());
            BufferedImage imae = ImageIO.read(l_url);
            Image imge = SwingFXUtils.toFXImage(imae, null);
            ImageView imgView = new ImageView(imge);
            imgView.setFitWidth(800);
            imgView.setFitHeight(150);
            // Event Information : HBOX
            HBox eventInfo = new HBox();
            // Event Date : Anchorpane
            AnchorPane Date = new AnchorPane();
            Date.setMaxWidth(100);
            Date.setMaxHeight(100);
            ImageView Calendar = new ImageView();
            Calendar.setImage(new Image("/Images/Calendar.png"));
               
            Label month = new Label(getMonthForInt(e.getDate().getMonth()).substring(0,3));
            Label day = new Label(String.valueOf(e.getDate().getDay()));
            month.setMinWidth(105);
            month.setStyle(monthLabel);
            day.setStyle(dayLabel);
            Date.getChildren().add(Calendar);
            Date.getChildren().add(month);
            Date.getChildren().add(day);
            // Event Name , place , date span : VBox
            VBox Vb = new VBox();
            //Vb.setPadding(new Insets(15,15,15,15));
            Vb.setMinWidth(570);
            Label Name=new Label(e.getName());  
            Name.setStyle(NameLabel);
            Label Place = new Label("Place : " + e.getPlace());
            Place.setStyle(NameLabel);
            Vb.getChildren().add(Name);
            Vb.getChildren().add(Place);          
            eventInfo.getChildren().add(Date);
            eventInfo.getChildren().add(Vb);

            VBox actions = new VBox();
            Button modifier = new Button("Update");
            modifier.setStyle("-fx-background-color : #009DF8; -fx-text-fill : #ffffff; -fx-border-color : #ffffff; -fx-border-width : 2px;");
            modifier.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) 
                {
                    
                    FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Event/CreateEvent.fxml"));
                    try {
                        content = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    CreateEventController sec = loader.getController();
                    sec.setEvenement(e);
                    sec.setModif(true);
                    try {
                        sec.fillForm();
                    } catch (Exception ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    parent.getChildren().set(0, content);
                    content.setLayoutX(20);
                    content.setLayoutY(137);
                }
                });
            Button supprimer = new Button("Delete");
            supprimer.setStyle("-fx-background-color : #ff1919; -fx-text-fill : #ffffff; -fx-border-color : #ffffff; -fx-border-width : 2px;");
            supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) 
                {  
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Suppression évènement");
                    alert.setContentText("Voulez vous vraiment supprimer:" + e.getName()+ "?");
                    java.util.Optional<javafx.scene.control.ButtonType> answer = alert.showAndWait();
                        if (answer.get() == javafx.scene.control.ButtonType.OK) 
                        {
                        es.delete(e);
                        try
                        {
                        ShowAll();
                        }
                        catch(Exception e)
                        {   
                        }
                        }
                }
                });
            modifier.setMinWidth(120);
            supprimer.setMinWidth(120);
            Label s = new Label("");
            Label se = new Label("");
            s.setMinHeight(5);
            se.setMinHeight(5);
            actions.getChildren().add(s);
            actions.getChildren().add(modifier);
            actions.getChildren().add(se);
            actions.getChildren().add(supprimer);
            eventInfo.getChildren().add(actions);
            eventInfo.setOnMouseClicked( ( ev ) ->
            {
                FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Event/EventDetails.fxml"));
                try {
                    content = loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                parent.getChildren().set(0, content);
                content.setLayoutX(20);
                content.setLayoutY(137);
                EventDetailsController sec = loader.getController();
                sec.setEvent(e);
                try {
                    sec.fillForm();
                } catch (Exception ex) {
                    Logger.getLogger(ClubDetailsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } );
            
           Container.getChildren().add(imgView);
           Container.getChildren().add(eventInfo);
           Label sep0 = new Label("");
           
           Label sep = new Label("");
           sep.setMinHeight(40);
           Container.getChildren().add(sep);
    }
        // Finally add all the events inside the Scrollpane to the main Content Anchorpane
        content.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background : #DDEBE9");
    }
    
    final String monthLabel = "-fx-padding: 20;\n" +
    "    -fx-text-fill: #ffffff;\n" +
    "    -fx-spacing: 0;\n" +
    "    -fx-alignment: center;\n" +
    "    -fx-font-weight: bold;\n" +
    "    -fx-font-size: 20;";

    final String dayLabel = "-fx-padding: 45 0 0 45;\n" +
    "    -fx-spacing: 0;\n" +
    "    -fx-alignment: center;\n" +
    "    -fx-text-fill: #000000;\n" +
    "    -fx-font-weight: bold;\n" +
    "    -fx-font-size: 30;";

    final String NameLabel = "-fx-padding: 15 0 0 0;\n" +
    "    -fx-spacing: 0;\n" +
    "    -fx-text-fill: #333333;\n" +
    "    -fx-font-weight: bold;\n" +
    "    -fx-font-size: 21;";

    @FXML
    private void join(ActionEvent event) {
        ClubService cs = new ClubService();
        if(cs.getMemberShipStatus(club.getId(), SingletonNavigation.getInstance().getLoggedInUser().getId()))
        {
            cs.removeMember(club.getId(), SingletonNavigation.getInstance().getLoggedInUser().getId());
            join_btn.setText("Join Club");
        }
        else
        {
            cs.addMember(club.getId(), SingletonNavigation.getInstance().getLoggedInUser().getId());
            join_btn.setText("Leave Club");
        }
        afficherMembres();
    }

    @FXML
    private void back(MouseEvent event) throws Exception{
        ShowAll();
    }
    
    public void afficherMembres()
    {
        if(members.getChildren()!=null)
        {
            members.getChildren().clear();
        }
        
        // get Elements to display 
        ClubService cs = new ClubService();
        List<User> myList=new ArrayList<User>();
        myList = cs.getMemberList(club.getId());
        VBox Container = new VBox();  
        
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(800, 400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(800);
        Container.setPrefHeight(400);
        
        members.setRightAnchor(scrollPane, 0.);
        members.setBottomAnchor(scrollPane, 0.);
        members.setLeftAnchor(scrollPane, 0.);
            
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
        members.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background :   #DDEBE9; -fx-border-color: #ffffff");

    }
         
}
