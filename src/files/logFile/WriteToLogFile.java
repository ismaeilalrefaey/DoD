package files.logFile;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Arrays;

public class WriteToLogFile {
    public static void clearTheFile() {
        try {
            try (FileWriter fwOb = new FileWriter("logs.txt", false)) {
                fwOb.flush();
            } 
        } catch (IOException ex) {
            Logger.getLogger(WriteToLogFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void write(String s){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("logs.txt" ,true);  
            fileWriter.write(s + "\n");
            System.out.println( s ); // in GUI - put commend it in console
        } catch(IOException ex) {
            System.err.println("There is an ERROR!! " + Arrays.toString(ex.getStackTrace()));
        } finally {
            try{
                fileWriter.close();
            } catch (IOException ex) {
                System.err.println("There is an ERROR!! " + Arrays.toString(ex.getStackTrace()));
            }
        }
    }
}
