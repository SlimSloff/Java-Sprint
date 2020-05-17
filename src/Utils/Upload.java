/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 *
 * @author Dell
 */
public class Upload 
{
        private static final String[] VALID_EXTENSIONS = new String[] {".png", ".jpg", "jpeg", "gif", "bmp"};

        private static boolean copyFileUsingStream(File source, File dest) throws Exception 
        {
            InputStream is = null;
            OutputStream os = null;
            try 
            {
                is = new FileInputStream(source);
                os = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);}
            } finally {
                is.close();
                os.close();
            }
            return true;
        }
        
        public static String choose() throws Exception
        {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null)
        {
            String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."),selectedFile.getName().length());
            if(Arrays.asList(VALID_EXTENSIONS).contains(fileExtension))
            {

            File source = new File(selectedFile.getPath());
            File dest = new File("C:\\wamp64\\www\\ecolewamp\\"+selectedFile.getName());
            if(copyFileUsingStream(source, dest))
            {
            return selectedFile.getName();
            }

            }
            else
            {
                System.out.println("Extension Invalide");
            }
        }
        else
        {
            System.out.println("Ficher non valide");
        }
        return "";
    }

  
}
