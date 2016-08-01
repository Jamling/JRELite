package cn.ieclipse.jrelite;

class Config {
    private boolean ignoreSysOut = true;
    private boolean ignore3rdLib = true;
    private boolean alwaysUnzipLib = false;
    private boolean cleanDirOfJar = true;
    private boolean useDefaultJre = false;
    
    private String unzipedLibPath = "temp\\unziped_lib";
    private String rezipedLibPath = "temp\\reziped_lib";
    private String customJrePath = "";
    private String jrePath = "C:\\Program Files\\Java\\jdk1.6.0_26";
    
    public boolean isIgnoreSysOut() {
        return ignoreSysOut;
    }
    
    public void setIgnoreSysOut(boolean ignoreSysOut) {
        this.ignoreSysOut = ignoreSysOut;
    }
    
    public boolean isIgnore3rdLib() {
        return ignore3rdLib;
    }
    
    public void setIgnore3rdLib(boolean ignore3rdLib) {
        this.ignore3rdLib = ignore3rdLib;
    }
    
    public boolean isAlwaysUnzipLib() {
        return alwaysUnzipLib;
    }
    
    public void setAlwaysUnzipLib(boolean alwaysUnzipLib) {
        this.alwaysUnzipLib = alwaysUnzipLib;
    }
    
    public boolean isCleanDirOfJar() {
        return cleanDirOfJar;
    }
    
    public void setCleanDirOfJar(boolean cleanDirOfJar) {
        this.cleanDirOfJar = cleanDirOfJar;
    }
    
    public boolean isUseDefaultJre() {
        return useDefaultJre;
    }
    
    public void setUseDefaultJre(boolean useDefaultJre) {
        this.useDefaultJre = useDefaultJre;
    }
    
    public String getUnzipedLibPath() {
        return unzipedLibPath;
    }
    
    public void setUnzipedLibPath(String unzipedLibPath) {
        this.unzipedLibPath = unzipedLibPath;
    }
    
    public String getRezipedLibPath() {
        return rezipedLibPath;
    }
    
    public void setRezipedLibPath(String rezipedLibPath) {
        this.rezipedLibPath = rezipedLibPath;
    }
    
    public String getCustomJrePath() {
        return customJrePath;
    }
    
    public void setCustomJrePath(String customJrePath) {
        this.customJrePath = customJrePath;
    }
    
    public String getJrePath() {
        if (isUseDefaultJre()) {
            jrePath = System.getProperty("JAVA_HOME");
        }
        if (jrePath == null || "".equals(jrePath.trim())) {
            jrePath = customJrePath;
        }
        return jrePath;
    }
    
    public void setJrePath(String jrePath) {
        this.jrePath = jrePath;
    }
    
    private static Config instance = new Config();
    
    public static Config getInstance() {
        return instance;
    }
    
    public static void main(String[] args) {
        Config cfg = new Config();
        System.out.println(cfg.getJrePath());
    }
}
