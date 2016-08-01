/**
 * 
 */
package cn.ieclipse.jrelite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Jamling
 *         
 */
public class Extract {
    
    public void start(Map<LibBean, HashSet<String>> libs) {
        // Collection<File> files = FileUtils.listFiles(new File(Config
        // .getInstance().getRezipedLIbPath()), new String[] { "txt" },
        // false);
        // for (final File file : files) {
        // if (Config.getInstance().isAlwaysUnzipLib() || !file.exists()) {
        // Thread thread = new Thread() {
        //
        // };
        // }
        // }
        File f = new File(Config.getInstance().getUnzipedLibPath(),
                "unzip.bat");
        FileUtils.deleteQuietly(f);
        for (final LibBean lib : libs.keySet()) {
            final File dir = new File(Config.getInstance().getUnzipedLibPath(),
                    FilenameUtils.getBaseName(lib.getName()));
                    
            if (Config.getInstance().isAlwaysUnzipLib()
                    || (!dir.exists() || dir.listFiles().length == 0)) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        // System.out.println("dir:" + dir.getAbsolutePath());
                        if (dir.mkdir() || dir.listFiles().length == 0) {
                            ArrayList<String> lines = new ArrayList<String>();
                            String jarPath = new File(
                                    Config.getInstance().getJrePath()
                                            + "/bin/jar.exe").getAbsolutePath();
                            String libPath = lib.getPath();
                            String format = String.format("\"%s\" xf \"%s\"",
                                    jarPath, libPath);
                            lines.add("cd " + dir.getName());
                            lines.add(format);
                            lines.add("cd ..");
                            try {
                                FileUtils.writeLines(
                                        new File(dir.getParent(), "unzip.bat"),
                                        lines, true);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // StringBuilder cmd = new StringBuilder();
                            // cmd.append("\"");
                            // cmd.append(new
                            // File("unzip.bat").getAbsolutePath());
                            // cmd.append("\" \"");
                            // cmd.append(new File(Config.getInstance()
                            // .getJrePath()).getAbsolutePath());
                            // cmd.append("\" \"");
                            // cmd.append(new File(lib.getPath())
                            // .getAbsolutePath());
                            // cmd.append("\"");
                            // System.out.println(cmd.toString());
                            // try {
                            // Process p = Runtime.getRuntime().exec(
                            // cmd.toString(), null, dir);
                            // p.waitFor();
                            // p.destroy();
                            // } catch (IOException e) {
                            // // TODO Auto-generated catch block
                            // e.printStackTrace();
                            // } catch (Exception e) {
                            // e.printStackTrace();
                            // }
                        }
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Classify classify = new Classify();
        classify.start();
        Extract extract = new Extract();
        extract.start(classify.libs);
    }
    
}
