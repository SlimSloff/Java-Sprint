/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Club;

import Entities.Event;
import Services.ClubService;
import Services.EventService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kinto
 */
public class StatistiquesController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane pie;
    @FXML
    private AnchorPane bar;
    private ClubService cs = new ClubService();
    private EventService es = new EventService();
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Music", cs.nbrClubParType("Musique")),
                new PieChart.Data("Sports", cs.nbrClubParType("Sportif")),
                new PieChart.Data("Social", cs.nbrClubParType("Social"))
                );
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Club Types");
        pie.getChildren().add(chart);
        
        
        bc.setTitle("Number of participants per event");
        xAxis.setLabel("Event");       
        yAxis.setLabel("number of participants");
 
        for(Event e : es.getEvents())
        {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(e.getName());       
        series1.getData().add(new XYChart.Data("", es.getNbrParticipants(e)));
        bc.getData().add(series1);
        }
        
        
        bar.getChildren().add(bc);
    }    
    
}
