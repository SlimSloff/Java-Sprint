/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Event;

//import Entities.Event;
import Entities.User;
import Controllers.NavigationController;
import Entities.Club;
import Entities.Event;
import Services.EventService;
import javafx.event.ActionEvent;
//import Services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import Utils.DatabaseConnection;
import Utils.Misc;
import Utils.SingletonNavigation;
import Utils.Upload;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;


/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CreateEventController implements Initializable {
    
    String SelctedCover = "default_event.png";
    private Event evenement;
    private boolean modif = false;

    @FXML
    private ImageView Cover;
    @FXML
    private DatePicker EventDate;
    @FXML
    private TextField libelle;
    @FXML
    private TextField lieu;
    @FXML
    private TextArea description;
    
    private int club_id;
    private Club club;
    @FXML
    private TextField duration;

    public void setClub(Club club) {
        this.club = club;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }
    
    public Event getEvenement() {
        return evenement;
    }

    public void setEvenement(Event evenement) {
        this.evenement = evenement;
    }

    public boolean isModif() {
        return modif;
    }

    public void setModif(boolean modif) {
        this.modif = modif;
    }
    
    @FXML
    private Button btnAjouter;

    /**
     * Initializes the controller class.
     */
    EventService ES=new EventService();
   
    public void fillForm() throws Exception
    {
       libelle.setText(evenement.getName());
       description.setText(evenement.getDescription());
       lieu.setText(evenement.getPlace());
       duration.setText(String.valueOf(evenement.getDuration()));
       URL l_url = new URL("http://localhost/ecolewamp/"+evenement.getCover());
       BufferedImage imae = ImageIO.read(l_url);
       Image imge = SwingFXUtils.toFXImage(imae, null);
       this.Cover.setImage(imge);
       this.SelctedCover = evenement.getCover();
    }
      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }    
   
    @FXML
    private void Ajouter(ActionEvent event)throws Exception 
    {    
        String errorlog = "";
          if(libelle.getText().equals("") || description.getText().equals("") || duration.getText().equals("") || lieu.getText().equals(""))
          {
              errorlog = "Vous devez d'abord remplir tous les champs !\n";
          }
        
          if(!duration.getText().matches("^[0-9]*$"))
          {
              errorlog += "Vous devez saisir une valeur numérique (Nombre de place) !\n";
          }
          
          if(EventDate.getValue()== null)
          {
              errorlog += "Vous devez choisir une date !";
          }
        if(errorlog.equals(""))
        {    
        if(modif == false)
        {
            DateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
            ES.insert(new Event(club_id, libelle.getText(),description.getText(), Date.valueOf( dateformat.format(EventDate.getValue().atStartOfDay(ZoneId.of("GMT")).toEpochSecond()* 1000)), 
            Integer.valueOf(duration.getText()), lieu.getText(), SelctedCover));
            Misc.showTrayNotification("Success", "Your event has been created !");
            SingletonNavigation.getInstance().getNavCont().clubs(event);
        }
        else
        {
         DateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
         Event e = new Event(club_id, libelle.getText(),description.getText(), Date.valueOf( dateformat.format(EventDate.getValue().atStartOfDay(ZoneId.of("GMT")).toEpochSecond()* 1000)), 
            Integer.valueOf(duration.getText()), lieu.getText(), SelctedCover);
         e.setId(evenement.getId());
         ES.update(e);
         //SingletonNavigation.getInstance().getNavCont().loadEvents(event);
        }
    
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText(errorlog);
            alert.show();
        }
    
    }

    @FXML
    private void chooseEventCover(MouseEvent event) throws Exception{
        SelctedCover = Upload.choose();
        URL l_url = new URL("http://localhost/ecole/"+SelctedCover);
        BufferedImage imae = ImageIO.read(l_url);
        Image imge = SwingFXUtils.toFXImage(imae, null);
        this.Cover.setImage(imge);
        
    }


    
}
