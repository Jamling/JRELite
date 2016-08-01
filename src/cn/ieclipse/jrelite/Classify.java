package cn.ieclipse.jrelite;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import cn.ieclipse.util.LineFileReader;
import cn.ieclipse.util.LineFileWriter;
import cn.ieclipse.util.LineFileReader.LineReader;

public class Classify {
    
    private static final String CLASS_LIST_FILE = "temp/classlist.txt";
    
    private static final String START_LOAD = "[Loaded";
    private static final String START_OPEN = "[Opened";
    private static final String START_SHARE = "shared objects file";
    
    Map<LibBean, HashSet<String>> libs = new HashMap<LibBean, HashSet<String>>();
    
    public static void main(String[] args) {
        Classify classify = new Classify();
        classify.start();
    }
    
    public void start() {
        
        LineReader lineReader = new LineReader() {
            @Override
            public void lineReaded(String line) {
                LineFileWriter fw = new LineFileWriter(
                        "temp\\log_" + 0 + ".txt", true);
                // fw.write("[untracked class list]");
                int limit = 4;
                String[] strs = line.split(" ", 4);
                if (Config.getInstance().isIgnoreSysOut()) {
                    if (!strs[0].equals(START_OPEN)
                            && !strs[0].equals(START_LOAD)
                            && !line.endsWith("]")) {
                        fw.write("ignore system out:" + line);
                        fw.close();
                        return;
                    }
                }
                if (strs.length < limit) {
                    fw.write("unknown class loader info:" + line);
                }
                else {
                    
                    if (strs[0].equals(START_LOAD)) {
                        String path = strs[3].substring(0,
                                strs[3].length() - 1);
                        String className = strs[1];
                        if (START_SHARE.equals(path)) {
                            String rtPath = new File(
                                    Config.getInstance().getJrePath(),
                                    "jre/lib/rt.jar").getAbsolutePath();
                            LibBean lib = new LibBean("rt.jar", rtPath);
                            lib.setJreLib(true);
                            addLibs(lib, className);
                        }
                        else if (path.endsWith(".jar")) {
                            LibBean lib = resolve(path);
                            addLibs(lib, className);
                            if (lib.isJreLib()) {
                                fw.write("find jre lib:" + lib.getPath());
                            }
                            else {
                                fw.write("find 3rd lib:" + lib.getPath());
                            }
                        }
                    }
                }
                fw.close();
            }
            
            @Override
            public void fileEnd() {
                for (LibBean lib : libs.keySet()) {
                    File file = new File(
                            Config.getInstance().getUnzipedLibPath(),
                            lib.getName() + ".txt");
                            
                    Set<String> set = libs.get(lib);
                    try {
                        FileUtils.writeLines(file, set);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            
            private LibBean resolve(String path) {
                LibBean lib = new LibBean(path);
                int pos = path.lastIndexOf('\\');
                if (pos >= 0) {
                    String libname = path.substring(pos + 1);
                    lib.setName(libname);
                    lib.setJreLib(true);
                }
                else {
                    pos = path.lastIndexOf('/');
                    if (pos >= 0) {
                        String libname = path.substring(pos + 1);
                        lib.setName(libname);
                        lib.setJreLib(false);
                        // System.out.println("be:" + path);
                        if (path.startsWith("file:/")) {
                            lib.setPath(
                                    new File(path.substring("file:/".length()))
                                            .getAbsolutePath());
                                            
                        }
                        // System.out.println("af:" + lib.getPath());
                    }
                }
                return lib;
            }
        };
        
        LineFileReader lfr = new LineFileReader(lineReader);
        try {
            lfr.read(CLASS_LIST_FILE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void addLibs(LibBean lib, String clazz) {
        HashSet<String> set = libs.get(lib);
        if (set == null) {
            set = new HashSet<String>();
            libs.put(lib, set);
        }
        String name = clazz.replace('.', '\\');
        name += ".class";
        set.add(name);
    }
}
