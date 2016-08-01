/**
 * 
 */
package cn.ieclipse.jrelite;

/**
 * @author Jamling
 *         
 */
public class LibBean {
    private String name;
    private String path;
    private boolean jreLib;
    
    public LibBean(String path) {
        super();
        this.path = path;
    }
    
    public LibBean(String name, String path) {
        super();
        this.name = name;
        this.path = path;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public boolean isJreLib() {
        return jreLib;
    }
    
    public void setJreLib(boolean jreLib) {
        this.jreLib = jreLib;
    }
    
    @Override
    public int hashCode() {
        return path.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LibBean) {
            if (name != null) {
                return path.equals(((LibBean) obj).path);
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%s[%s]", name, path);
    }
}
