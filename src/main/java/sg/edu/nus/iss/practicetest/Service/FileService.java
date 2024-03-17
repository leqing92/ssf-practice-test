package sg.edu.nus.iss.practicetest.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.practicetest.Utils.Util;

@Service
public class FileService {
    
    public String readFile(){
        StringBuilder output = new StringBuilder();
        File file = new File(Util.TODOSTXT);

        if(file.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
        
                while ((line = br.readLine()) != null) {
                    output.append(line);
                }                    
                
            }catch(IOException e){
                System.out.println("IOException: " + e.getMessage());
            }
        }else{
            System.out.println("file not exist: " + Util.TODOSTXT);
        } 

        return output.toString();
    }
}
