/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Club;

import Entities.Club;
import Services.ClubService;
import Utils.Misc;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class ClubHomepageController implements Initializable {

    @FXML
    private AnchorPane canCenter;
    @FXML
    private AnchorPane optional;
    @FXML
    private AnchorPane content;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField searchTerm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            ShowAll();
        } catch (Exception ex) {
            Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void ShowAll() throws Exception{
        // clear existing content if it exists  
        if(content.getChildren()!=null)
        {
            content.getChildren().clear();
        }
        // get Elements to display 
        ClubService cs=new ClubService();
        List<Club> myList=new ArrayList<Club>();
        myList = cs.getAll();
   
        GridPane Container = new GridPane();  // main container for all data specific to a club
        Container.setAlignment(Pos.CENTER);
        // Scroll pane to display all the found clubs
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(950, 630);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(950);
        Container.setPrefHeight(630);
        content.setRightAnchor(scrollPane, 0.);
        content.setBottomAnchor(scrollPane, 0.);
        content.setLeftAnchor(scrollPane, 0.);
        Container.setPadding(new Insets(20,20,20,20));

        
        // iterate through all services and create a service element
        int i = 0;
        int j = 0;
        for (Club  c : myList)
        {
            //HBox : single with spacing
            HBox Hb = new HBox();
            //VBox : single
            VBox Vb = new VBox();
            URL l_url = new URL("http://localhost/ecolewamp/"+c.getLogo());
            BufferedImage imae = ImageIO.read(l_url);
            Image imge = SwingFXUtils.toFXImage(imae, null);
            ImageView img = new ImageView(imge);
            img.setFitHeight(240);
            img.setFitWidth(240);
            
            Label name = new Label(c.getName());
            name.setStyle("-fx-alignment : center; -fx-text-fill : #ff7f50; -fx-font-wieght : bold; -fx-font-size : 25");
            name.setPrefWidth(240);

            Label type = new Label("Activity : " + c.getType());
            type.setPrefWidth(240);
            type.setStyle("-fx-alignment : center; -fx-font-weight : bold; -fx-text-fill : #333333; -fx-font-size : 20;  -fx-font-wieght : bold; -fx-padding : 0 0 0 15;");
      
            HBox seep = new HBox();
            Label sap = new Label("");
            sap.setMinWidth(12);
            seep.getChildren().add(sap);

            Label margin = new Label("");
            margin.setMinHeight(250);
            Vb.getChildren().add(img);
            Vb.getChildren().add(margin);
            Vb.getChildren().add(name);
            Vb.getChildren().add(type);
            Vb.getChildren().add(seep);
     
            
            Label separator = new Label("");
            separator.setMinWidth(20);
            Hb.getChildren().add(Vb);
            Hb.getChildren().add(separator);
            AnchorPane ap = new AnchorPane();
            ap.getChildren().add(img);
            ap.getChildren().add(Hb);
            ap.setOnMouseClicked( ( e ) ->
            {
                FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Club/ClubDetails.fxml"));
                    try {
                        content = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ClubDetailsController sec = loader.getController();
                    sec.setClub(c);
                try {
                    sec.ShowAll();
                } catch (Exception ex) {
                    Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    try {
                        sec.fillForm();
                    } catch (Exception ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    root.getChildren().set(1, content);
                    content.setLayoutX(50);
                    content.setLayoutY(24);
            } );
            // Add all the club elements to the services container
            Container.add(ap, i, j);
            
            i++;
            if(i>3)
            {
                i = 0;
                j++;
            }
        }

        // Finally add all the events inside the Scrollpane to the main content Anchorpane
        content.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background : #DDEBE9");
    }

    @FXML
    private void loadCreate(ActionEvent event) throws Exception{
        content = FXMLLoader.load(getClass().getResource("/Views/Club/ClubForm.fxml"));
        root.getChildren().set(1, content);
        content.setLayoutX(50);
        content.setLayoutY(24);
    }

    @FXML
    public void showMine() throws Exception
    {
        // clear existing content if it exists  
        if(content.getChildren()!=null)
        {
            content.getChildren().clear();
        }
        
        // get Elements to display 
        ClubService cs=new ClubService();
        List<Club> myList=new ArrayList<Club>();
        myList = cs.getMine();
        VBox Container = new VBox();  // main container for all data specific to a club
        
        // Scroll pane to display all the found clubs
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(1100, 500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(1100);
        Container.setPrefHeight(500);
        content.setRightAnchor(scrollPane, 0.);
        content.setBottomAnchor(scrollPane, 0.);
        content.setLeftAnchor(scrollPane, 0.);
    
        Container.setPadding(new Insets(15,15,15,15));
        for(Club c : myList)
        {
            VBox element = new VBox();
            Label element_sep = new Label("");
            element_sep.setMinHeight(50);
            URL l_url = new URL("http://localhost/ecolewamp/"+c.getLogo());
            BufferedImage imae = ImageIO.read(l_url);
            Image imge = SwingFXUtils.toFXImage(imae, null);
            ImageView imgView = new ImageView(imge);
            imgView.setFitWidth(135);
            imgView.setFitHeight(135);
            // Club Information : HBOX
            HBox clubInfo = new HBox();
            Label sep0 = new Label("");
            sep0.setMinWidth(30);
            clubInfo.getChildren().add(sep0);
            clubInfo.getChildren().add(imgView);
            Label sep = new Label("");
            sep.setMinWidth(50);
            clubInfo.getChildren().add(sep);
            VBox vb = new VBox();
            String nomClub = "Club : " + c.getName();
            Label nom = new Label(nomClub);
            nom.setStyle(NameLabel);
            Label type = new Label("Activity : " + c.getType());
            type.setStyle(NameLabel);
            TextArea desc = new TextArea("Description : " + c.getDescription());
            desc.setMinWidth(600);
            desc.setMaxHeight(80);
            desc.setWrapText(true);
            desc.setStyle("-fx-background-color: none");
            desc.setEditable(false);
            desc.setScrollTop(20);
            vb.getChildren().add(nom);
            vb.getChildren().add(type);
            vb.getChildren().add(desc);
            Label separ = new Label("");
            separ.setMinWidth(50);
            VBox actions = new VBox();
            Button modifier = new Button("Update");
            modifier.setStyle("-fx-background-color : #009DF8; -fx-text-fill : #ffffff; -fx-border-color : #ffffff; -fx-border-width : 2px;");
            modifier.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) 
                {
                    
                    FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Club/ClubForm.fxml"));
                    try {
                        content = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ClubFormController sec = loader.getController();
                    sec.setClub(c);
                    sec.setModif(true);
                    try {
                        sec.fillForm();
                    } catch (Exception ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    root.getChildren().set(1, content);
                    content.setLayoutX(50);
                    content.setLayoutY(24);
                }
                });
            Button supprimer = new Button("Delete");
            supprimer.setStyle("-fx-background-color : #ff1919; -fx-text-fill : #ffffff; -fx-border-color : #ffffff; -fx-border-width : 2px;");
            supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) 
                {  
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Suppression produit");
                    alert.setContentText("Voulez vous vraiment supprimer:" + c.getName()+ "?");
                    java.util.Optional<javafx.scene.control.ButtonType> answer = alert.showAndWait();
                        if (answer.get() == javafx.scene.control.ButtonType.OK) 
                        {
                        cs.delete(c);
                        try
                        {
                        showMine();
                        String message = "Le club "+c.getName()+" a ete supprimé";
                        Misc.showTrayNotification("Supression", message);
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
            s.setMinHeight(20);
            se.setMinHeight(10);
            actions.getChildren().add(s);
            actions.getChildren().add(modifier);
            actions.getChildren().add(se);
            actions.getChildren().add(supprimer);
            
            clubInfo.getChildren().add(vb);
            clubInfo.getChildren().add(separ);
            clubInfo.getChildren().add(actions);
            clubInfo.setOnMouseClicked( ( e ) ->
            {
                
                FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Club/ClubDetails.fxml"));
                    try {
                        content = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ClubDetailsController sec = loader.getController();
                    sec.setClub(c);
                try {
                    sec.ShowAll();
                } catch (Exception ex) {
                    Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    try {
                        sec.fillForm();
                    } catch (Exception ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    root.getChildren().set(1, content);
                    content.setLayoutX(50);
                    content.setLayoutY(24);
                
            } );
            element.getChildren().add(clubInfo);
            element.getChildren().add(element_sep);
            // Add all the club elements to the event container
            Container.getChildren().add(element);   
        }
        // Finally add all the clubs inside the Scrollpane to the main content Anchorpane
        content.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background : #DDEBE9");
    }
    
         final String NameLabel = "-fx-padding: 0 5 0 0;\n" +
"    -fx-spacing: 0;\n" +
"    -fx-text-fill: #ffffff;\n" +
"    -fx-font-weight: bold;\n" +
"    -fx-font-size: 21;";

    @FXML
    private void Search(ActionEvent event) throws Exception{
        if(!searchTerm.getText().equals(""))
        {
            // clear existing content if it exists  
        if(content.getChildren()!=null)
        {
            content.getChildren().clear();
        }
        
        // get Elements to display 
        ClubService cs=new ClubService();
        List<Club> myList=new ArrayList<Club>();
        myList = cs.search(searchTerm.getText());
         GridPane Container = new GridPane();  // main container for all data specific to a club
        Container.setAlignment(Pos.CENTER);
        // Scroll pane to display all the found clubs
        ScrollPane scrollPane = new ScrollPane(Container);
        scrollPane.setPrefSize(950, 630);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane.setTopAnchor(scrollPane, 0.);
        Container.setPrefWidth(950);
        Container.setPrefHeight(630);
        content.setRightAnchor(scrollPane, 0.);
        content.setBottomAnchor(scrollPane, 0.);
        content.setLeftAnchor(scrollPane, 0.);
        Container.setPadding(new Insets(20,20,20,20));

        
        // iterate through all services and create a service element
        int i = 0;
        int j = 0;
        for (Club  c : myList)
        {
            //HBox : single with spacing
            HBox Hb = new HBox();
            //VBox : single
            VBox Vb = new VBox();
            URL l_url = new URL("http://localhost/ecolewamp/"+c.getLogo());
            BufferedImage imae = ImageIO.read(l_url);
            Image imge = SwingFXUtils.toFXImage(imae, null);
            ImageView img = new ImageView(imge);
            img.setFitHeight(240);
            img.setFitWidth(240);
            
            Label name = new Label(c.getName());
            name.setStyle("-fx-alignment : center; -fx-text-fill : #ff7f50; -fx-font-wieght : bold; -fx-font-size : 25");
            name.setPrefWidth(240);

            Label type = new Label("Activity : " + c.getType());
            type.setPrefWidth(240);
            type.setStyle("-fx-alignment : center; -fx-font-weight : bold; -fx-text-fill : #333333; -fx-font-size : 20;  -fx-font-wieght : bold; -fx-padding : 0 0 0 15;");
      
            HBox seep = new HBox();
            Label sap = new Label("");
            sap.setMinWidth(12);
            seep.getChildren().add(sap);

            Label margin = new Label("");
            margin.setMinHeight(250);
            Vb.getChildren().add(img);
            Vb.getChildren().add(margin);
            Vb.getChildren().add(name);
            Vb.getChildren().add(type);
            Vb.getChildren().add(seep);
     
            
            Label separator = new Label("");
            separator.setMinWidth(20);
            Hb.getChildren().add(Vb);
            Hb.getChildren().add(separator);
            AnchorPane ap = new AnchorPane();
            ap.getChildren().add(img);
            ap.getChildren().add(Hb);
            ap.setOnMouseClicked( ( e ) ->
            {
                FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Club/ClubDetails.fxml"));
                    try {
                        content = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ClubDetailsController sec = loader.getController();
                    sec.setClub(c);
                try {
                    sec.ShowAll();
                } catch (Exception ex) {
                    Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    try {
                        sec.fillForm();
                    } catch (Exception ex) {
                        Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    root.getChildren().set(1, content);
                    content.setLayoutX(50);
                    content.setLayoutY(24);
            } );
            // Add all the club elements to the services container
            Container.add(ap, i, j);
            i++;
            if(i>3)
            {
                i = 0;
                j++;
            }
        }

        // Finally add all the events inside the Scrollpane to the main content Anchorpane
        content.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-background : #DDEBE9");
        }
    }
    
   
    

}
