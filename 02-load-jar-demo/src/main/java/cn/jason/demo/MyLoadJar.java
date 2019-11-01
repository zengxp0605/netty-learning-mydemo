package cn.jason.demo;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyLoadJar {
    private final static String CLAZZ_SUFFIX = ".class";
    private ClassLoader classLoader;
    private String[] jarPathStringArr;

    public static void main(String[] args) throws Exception {
        new MyLoadJar().init();

    }

    public void init() throws Exception{
        String jarpath = "D:\\work\\_jarload"; // System.getProperty("user.dir") +File.separator+ "lib";

        File apiJarDirectory = new File(jarpath);
        File[] files = apiJarDirectory.listFiles();
        System.out.println("files: " + files);

        URL[] jarFilePathArr = new URL[files.length];
        jarPathStringArr = new String[files.length];
        int i = 0;
        for (File f: files) {
//            JarFile jf = new JarFile(f);
//            System.out.println("jf: " + jf.getManifest().getMainAttributes());

            String jarname = f.getName();
            if (jarname.indexOf(".jar") < 0) {
                continue;
            }
            String jarFilePath = "file:\\" + jarpath + File.separator
                    + jarname;
            jarPathStringArr[i] = jarpath + File.separator+ jarname;
            jarFilePathArr[i] = new URL(jarFilePath);
            System.out.println("f: " + f.getName() + " | " + jarFilePath);

            i++;
        }
        classLoader = new URLClassLoader(jarFilePathArr);

        loadAllJarFile();
    }

    public void loadAllJarFile() throws  Exception{
        for (String jarPath: jarPathStringArr) {
            Class demo = loadJar(jarPath);
            System.out.println("jarPath: " + jarPath);
            if(! jarPath.endsWith("import-jar-1.0.0.jar")){
                continue;
            }
            Object object = demo.newInstance();
            System.out.println(demo.getMethod("start", String.class).invoke(object, new Object[]{"str-amx"}));
        }
    }

    public Class loadJar(String jarName) {
        if (jarName.indexOf(".jar") < 0) {
            return null;
        }
        try {
            JarFile jarFile = new JarFile(jarName);
            Enumeration<JarEntry> em = jarFile.entries();
            while (em.hasMoreElements()) {
                JarEntry jarEntry = em.nextElement();
                String clazzFile = jarEntry.getName();

                System.out.println("clazzFile: " + clazzFile);
                if (!clazzFile.endsWith(CLAZZ_SUFFIX)) {
                    continue;
                }
                String clazzName = clazzFile.substring(0,
                        clazzFile.length() - CLAZZ_SUFFIX.length()).replace(
                        '/', '.');
                System.out.println("load clazzName: " + clazzName);
                return this.classLoader.loadClass(clazzName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
