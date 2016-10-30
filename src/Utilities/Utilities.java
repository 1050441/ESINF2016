package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 1050441
 */

public class Utilities {
    
    public static List<String> readFile(String path) throws IOException {
        
        List<String> activitiesList = new ArrayList<>();
        
	FileInputStream fis = new FileInputStream(path);
 
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                activitiesList.add(line);
            }
        }        
        return activitiesList;
    }
}