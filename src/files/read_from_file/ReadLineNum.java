package files.read_from_file;

import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.logging.Level;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadLineNum implements Callable< String > {
    private Stream< String > lines;
    private int idx;
    
    public ReadLineNum(String fileName ,int idx){
        this.idx = idx;
        
        try {
            lines = Files.lines( Paths.get(fileName) );
        } catch (IOException ex) {
            Logger.getLogger(ReadLineNum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String call() throws Exception {
        String line = lines.skip(this.idx).findFirst().get();
        return line;
    }
}