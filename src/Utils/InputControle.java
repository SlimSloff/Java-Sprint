/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class InputControle 
{
    
    public static boolean isPasswordValid(String password)
    {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern)?true:false;
    }
    
    public static boolean isEmailValid(String email)
    {
        String pattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        return email.matches(pattern)?true:false;
    }
    
    public static boolean isPhoneNumberValid(String phoneNumber)
    {   
        String pattern = "^[0-9]{8}$";
        return phoneNumber.matches(pattern)?true:false;
    }
    
    public static boolean isCharOnly(String text)
    {   
        String pattern = "[A-Za-z]*";
        return text.matches(pattern)?true:false;
    }
    
    public static boolean isNumbers(String text)
    {   
        String pattern = "^[0-9]*$";
        return text.matches(pattern)?true:false;
    }
    
    public static boolean isDouble(String text)
    {   
        String pattern = "\\d{0,7}([\\.]\\d{0,4})?";
        return text.matches(pattern)?true:false;
    }
    
    public static boolean isDateValid(Date debut, Date fin)
    {
        return (fin.compareTo(debut) > 0)?true:false;
    }
    
   
   
    
}
