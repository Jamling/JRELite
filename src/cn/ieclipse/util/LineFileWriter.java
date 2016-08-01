package cn.ieclipse.util;

import java.io.FileWriter;
import java.io.IOException;

public class LineFileWriter {
    FileWriter fw = null;
    
    public LineFileWriter(String name) {
        this(name, false);
    }
    
    public LineFileWriter(String name, boolean append) {
        try {
            fw = new FileWriter(name, append);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void write(String line) {
        if (fw != null) {
            try {
                fw.write(line);
                fw.write((char) 13);
                fw.write((char) 10);
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void close() {
        if (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
