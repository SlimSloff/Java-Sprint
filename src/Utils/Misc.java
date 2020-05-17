/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.text.DateFormatSymbols;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jdk.nashorn.internal.objects.NativeString;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Dell
 */
public class Misc 
{
    public static String getMonthForInt(int num) 
    {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return NativeString.substr(month,0,3);
    }
    
    public static void showDialog(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING, "alert box.", ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    
    public static void showTrayNotification(String title, String message)
    {
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndWait(); 
    }
}
