package cn.ieclipse.jrelite;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Packager {
    
    public void copy(Map<LibBean, HashSet<String>> libs) {
        File f = new File(Config.getInstance().getRezipedLibPath(),
                "rezip.bat");
        FileUtils.deleteQuietly(f);
        
        for (final LibBean lib : libs.keySet()) {
            if (Config.getInstance().isIgnore3rdLib() && !lib.isJreLib()) {
                continue;
            }
            String dirName = FilenameUtils.getBaseName(lib.getName());
            final File src = new File(Config.getInstance().getUnzipedLibPath(),
                    dirName);
            final File dst = new File(Config.getInstance().getRezipedLibPath(),
                    dirName);
                    
            File classSetFile = new File(
                    Config.getInstance().getUnzipedLibPath(),
                    lib.getName() + ".txt");
            try {
                FileUtils.copyDirectory(src, dst,
                        new CommonLibFilter(src.getPath(), classSetFile));
                String jarPath = new File(
                        Config.getInstance().getJrePath() + "/bin/jar.exe")
                                .getAbsolutePath();
                String format = String.format(
                        "\"%s\" cvfm %s %s\\META-INF\\MANIFEST.MF -C %s .",
                        jarPath, lib.getName(), dirName, dirName);
                ArrayList<String> lines = new ArrayList<String>();
                lines.add(format);
                FileUtils.writeLines(f, lines, true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (Config.getInstance().isCleanDirOfJar()) {
                deleteEmptyDir(dst);
            }
        }
        
    }
    
    public void deleteEmptyDir(File dir) {
        File[] subs = dir.listFiles();
        if (subs.length > 0) {
            for (File sub : subs) {
                if (sub.isDirectory()) {
                    deleteEmptyDir(sub);
                }
            }
        }
        
        if (dir.delete()) {
            System.out.println("empty dir(" + dir.getPath() + ") deleted!");
        }
    }
    
    public static void main(String[] args) {
        Classify classify = new Classify();
        classify.start();
        Extract extract = new Extract();
        extract.start(classify.libs);
        
        Packager packager = new Packager();
        packager.copy(classify.libs);
        
    }
}

class CommonLibFilter implements FileFilter {
    protected List<String> classSet;
    protected File classSetFile = null;
    protected String basePath;
    
    public CommonLibFilter(String basePath, File classSetFile) {
        this.basePath = basePath;
        if (!basePath.endsWith(System.getProperty("file.separator"))) {
            basePath += System.getProperty("file.separator");
        }
        System.out.println("base path: " + basePath);
        this.classSetFile = classSetFile;
        try {
            classSet = FileUtils.readLines(this.classSetFile);
        } catch (IOException e) {
            e.printStackTrace();
            classSet = new ArrayList<String>();
        }
    }
    
    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            if (pathname.listFiles() == null
                    || pathname.listFiles().length == 0) {
                return false;
            }
            // System.out.println("dir:" + pathname.getPath());
            return true;
        }
        else if (pathname.isFile()) {
            if (pathname.getName().endsWith(".class")) {
                String path = pathname.getPath()
                        .substring(basePath.length() + 1);
                        
                // System.out.println(path);
                if (classSet.contains(path)) {
                    // System.out.println(String.format("file %s copied!",
                    // path));
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                System.out.println(
                        "non-class file: " + pathname.getPath() + " copied");
                return true;
            }
        }
        System.out.println("unknow path : " + pathname);
        return false;
    }
}

class ResourcesLibFilter extends CommonLibFilter {
    
    public ResourcesLibFilter(String basePath, File classSetFile) {
        super(basePath, classSetFile);
    }
    
}