/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Login;

import Entities.User;
import Services.UserService;
import Utils.Misc;
import Utils.SingletonNavigation;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class LoginController implements Initializable {
    private int counter =0;
    private final double SLIDE_HEIGHT = 700;

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private ImageView imgblur;
    private static final Effect frostEffect =
    new BoxBlur(10, 10, 10);
    @FXML
    private AnchorPane temporary1;
    @FXML
    private AnchorPane slide1;
    @FXML
    private AnchorPane temporary2;
    @FXML
    private AnchorPane slide2;
    @FXML
    private AnchorPane temporary3;
    @FXML
    private AnchorPane slide3;
    @FXML
    private AnchorPane temporary4;
    @FXML
    private AnchorPane slide4;
    @FXML
    private AnchorPane temporary5;
    @FXML
    private AnchorPane slide5;
    @FXML
    private AnchorPane root;
            
    private String[] SLIDES = new String[] {"/Views/Login/slide1.fxml", "/Views/Login/slide2.fxml", "/Views/Login/slide3.fxml", "/Views/Login/slide4.fxml", "/Views/Login/slide5.fxml"};
    private void shuffle()
    {
        String tmp="";
        tmp=SLIDES[0];
        SLIDES[0]=SLIDES[1];
        SLIDES[1]=SLIDES[2];
        SLIDES[2]=SLIDES[3];
        SLIDES[3]=SLIDES[4];
        SLIDES[4]=tmp;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        imgblur.setEffect(frostEffect);
        AnimationPeriodique();
        try {
            slide1 = FXMLLoader.load(getClass().getResource("/Views/Login/slide1.fxml"));
            slide2 = FXMLLoader.load(getClass().getResource("/Views/Login/slide2.fxml"));
            slide3 = FXMLLoader.load(getClass().getResource("/Views/Login/slide3.fxml"));
            slide4 = FXMLLoader.load(getClass().getResource("/Views/Login/slide4.fxml"));
            slide5 = FXMLLoader.load(getClass().getResource("/Views/Login/slide5.fxml"));
            root.getChildren().set(1,slide1);
            root.getChildren().set(3,slide2);
            root.getChildren().set(5,slide3);
            root.getChildren().set(7,slide4);
            root.getChildren().set(9,slide5);
            slide1.setLayoutX(0);
            temporary1.setLayoutX(0);
            slide2.setLayoutX(240);
            temporary2.setLayoutX(240);
            slide3.setLayoutX(480);
            temporary3.setLayoutX(480);
            slide4.setLayoutX(720);
            temporary4.setLayoutX(720);
            slide5.setLayoutX(960);
            temporary5.setLayoutX(960);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void Login(ActionEvent event) throws Exception {
       UserService us = new UserService();
        if(email.getText().equals("admin") && password.getText().equals("admin"))
        {
            SingletonNavigation MyNavigation = SingletonNavigation.getBackInstance();
            MyNavigation.setLoggedInUser(us.getUserByUsername(email.getText()));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow() ;
            stage.setScene(new Scene(MyNavigation.getRoot()));
            stage.show();  
            MyNavigation.getBackCont().initsidebar();
        }
        else
        {
            User u = us.getUserByUsername(email.getText());
            if(u != null)
            {
                if(BCrypt.checkpw(password.getText(),u.getPassword().substring(0,2)+"a"+u.getPassword().substring(3)))
                {
                    SingletonNavigation MyNavigation = SingletonNavigation.getInstance();
                    MyNavigation.setLoggedInUser(u);
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow() ;
                    stage.setScene(new Scene(MyNavigation.getRoot()));
                    stage.show();
                }
            }
            else
            {
                Misc.showDialog("login ou mot de passe erroné", "veuillez vous verifier vos paramétres !");
            }
        }
    }

    @FXML
    private void Inscrit(ActionEvent event) throws Exception{
     
    }
    
    
    public void translate(int offset,AnchorPane temporary, AnchorPane slide, String nextSlide, String lastSlide) throws Exception
    {
       temporary = FXMLLoader.load(getClass().getResource(lastSlide));
       
       slide= FXMLLoader.load(getClass().getResource(nextSlide));
       
       slide.setLayoutX(100);
       temporary.setLayoutX(100);
       slide.translateYProperty().set(SLIDE_HEIGHT);
       root.getChildren().set(offset, slide);
       root.getChildren().set(offset+1, temporary);
       
       int layX=0;
       if(offset == 0)
           layX=0;
       else if(offset == 2)
           layX=240;
       else if(offset == 4)
           layX=480;
       else if(offset == 6)
           layX=720;
       else if(offset == 8)
           layX=960;
       slide.setLayoutX(layX);
       temporary.setLayoutX(layX);
          
       Timeline timeline = new Timeline();
       KeyValue kv = new KeyValue(slide.translateYProperty(), 0, Interpolator.EASE_IN);
       KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
       timeline.getKeyFrames().add(kf);
       TranslateTransition translate = new TranslateTransition(Duration.seconds(1.08), temporary);
       translate.setToY(-SLIDE_HEIGHT);
      
       timeline.play();
       translate.play();
    }
    
    
    public void AnimationPeriodique()
    {
        AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) 
        {
            counter++;
            if(counter==200)
            {
                try 
                {
                    for(int i=0;i<9;i+=2)
                    {
                        if(i==0)
                        translate(i, temporary1, slide1, SLIDES[1], SLIDES[0]);
                        if(i==2)
                        translate(i, temporary2, slide2, SLIDES[2], SLIDES[1]);
                        if(i==4)
                        translate(i, temporary3, slide3, SLIDES[3], SLIDES[2]);
                        if(i==6)
                        translate(i, temporary4, slide4, SLIDES[4], SLIDES[3]);
                        if(i==8)
                        translate(i, temporary2, slide2, SLIDES[0], SLIDES[4]);
                    }
                    
                  
                    
                } 
                catch (Exception ex)
                {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                shuffle();
                counter=0;
            }
        }
    };
    timer.start();
    }
    
}
