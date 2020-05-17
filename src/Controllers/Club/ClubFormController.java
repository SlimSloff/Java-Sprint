/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Club;

import Entities.Club;
import Services.ClubService;
import Utils.Misc;
import Utils.SingletonNavigation;
import Utils.Upload;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author kinto
 */
public class ClubFormController implements Initializable {

    @FXML
    private TextField libelle;
    @FXML
    private ComboBox<String> type;
    @FXML
    private Button btnAjouter;
    @FXML
    private TextArea description;
    
    private ClubService cs = new ClubService();
    String SelctedCover = "club.png";
    private Club club;
    @FXML
    private ImageView image;
    @FXML
    private Label title;
    
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public boolean isModif() {
        return modif;
    }

    public void setModif(boolean modif) {
        this.modif = modif;
    }
    
    private boolean modif = false;
    
    public void fillForm() throws Exception
    {
       title.setText("Update your club");
       libelle.setText(club.getName());
       description.setText(club.getDescription());
       type.setValue(club.getType());
       URL l_url = new URL("http://localhost/ecolewamp/"+club.getLogo());
       BufferedImage imae = ImageIO.read(l_url);
       Image imge = SwingFXUtils.toFXImage(imae, null);
       this.image.setImage(imge);
       this.SelctedCover = club.getLogo();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        type.setItems(FXCollections.observableArrayList("Musique", "Sport", "Social"));
        type.setValue("Musique");
    }    

    @FXML
    private void chooselogo(ActionEvent event) throws Exception {
        SelctedCover = Upload.choose();
        URL l_url = new URL("http://localhost/ecolewamp/"+SelctedCover);
        BufferedImage imae = ImageIO.read(l_url);
        Image imge = SwingFXUtils.toFXImage(imae, null);
        this.image.setImage(imge);
    }

    @FXML
    private void Ajouter(ActionEvent event) throws Exception {
        String errorlog = "";
        if(libelle.getText().equals("") || description.getText().equals(""))
        {
            errorlog = "Vous devez d'abord remplir tous les champs !\n";
        }
        if(errorlog.equals(""))
        {    
            if(modif == false)
            {
                Club c = new Club(libelle.getText(), SelctedCover, description.getText(), type.getValue(), SingletonNavigation.getInstance().getLoggedInUser().getId());
                cs.insert(c);
                cs.addMember(cs.getClubId(libelle.getText()), SingletonNavigation.getInstance().getLoggedInUser().getId());
                String title = "Club crée";
                String message = "Le club "+c.getName()+" a ete créé";
                Misc.showTrayNotification(title, message);
                SingletonNavigation.getInstance().getNavCont().loadMyClubs(event);
            }
            else
            {
                Club c = new Club(libelle.getText(), SelctedCover, description.getText(), type.getValue());
                c.setId(club.getId());
                cs.update(c);
                String title = "Club modifié";
                String message = "Le club "+c.getName()+" a ete modifié avec succes";
                Misc.showTrayNotification(title, message);
                SingletonNavigation.getInstance().getNavCont().loadMyClubs(event);
            }

        }
        else
        {
            Misc.showDialog("Warning", errorlog);
        }
    }
    
}
