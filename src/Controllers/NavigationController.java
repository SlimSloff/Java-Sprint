/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.Club.ClubDetailsController;
import Controllers.Club.ClubHomepageController;
import Entities.Club;
import Utils.SingletonNavigation;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class NavigationController implements Initializable {

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
 
    }    

   
    
    @FXML
    private void Acceuil(ActionEvent event) throws Exception
    {
       
    }

    @FXML
    public void clubs(ActionEvent event) throws Exception{
        try 
        {
            content = FXMLLoader.load(getClass().getResource("/Views/Club/ClubHomepage.fxml"));
            root.getChildren().set(1, content);
            content.setLayoutX(0);
            content.setLayoutY(70);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(BackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadMyClubs(ActionEvent event) throws Exception{
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Views/Club/ClubHomepage.fxml"));
            try {
            content = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(NavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClubHomepageController sec = loader.getController();
        try {
            sec.showMine();
        } catch (Exception ex) {
            Logger.getLogger(ClubHomepageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        root.getChildren().set(1, content);
        content.setLayoutX(0);
        content.setLayoutY(70);
    }
    

}
