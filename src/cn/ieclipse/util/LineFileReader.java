/**
 * 
 */
package cn.ieclipse.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Jamling
 *         
 */
public class LineFileReader {
    
    private LineReader lineReader;
    
    public void setLineReader(LineReader lineReader) {
        this.lineReader = lineReader;
    }
    
    public LineFileReader() {
        this(null);
    }
    
    public LineFileReader(LineReader lineReader) {
        this.lineReader = lineReader;
    }
    
    public static interface LineReader {
        void lineReaded(String line);
        
        void fileEnd();
        
    }
    
    public void read(String path) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path)));
            String line = br.readLine();
            if (lineReader != null) {
                while (line != null) {
                    lineReader.lineReaded(line);
                    line = br.readLine();
                }
            }
        } finally {
            if (lineReader != null) {
            
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e2) {
                    throw e2;
                }
            }
            if (lineReader != null) {
                lineReader.fileEnd();
            }
        }
    }
}
