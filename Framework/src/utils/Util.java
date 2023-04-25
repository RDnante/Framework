package utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.xml.parsers.*;
import java.io.*;
import org.w3c.dom.*;

public class Util {

    public Util() {
    }

    public List<String> getFolderClass(File f) throws Exception {
        try {
            List<String> list = new ArrayList<>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(f);

            Element racine = document.getDocumentElement();

            NodeList racinenoeud = racine.getChildNodes();

            for (int i = 0; i < racinenoeud.getLength(); i++) {
                if (racinenoeud.item(i).getNodeType() == Node.ELEMENT_NODE && racinenoeud.item(i).getNodeName() == "class-folder") {
                    Element folder = (Element) racinenoeud.item(i);
                    Element nom = (Element) folder.getElementsByTagName("name").item(0);
                    list.add(nom.getTextContent());
                }
            }

            return list;
        } catch (Exception e) {
            // Exception ex = new Exception("verifier le fichier");
            throw e;
        }
    }

    public List<Class<?>> getClassesInPackage(String packageName,PrintWriter out) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        out.println(path);
        for (java.net.URL resource : java.util.Collections.list(classLoader.getResources(path))) {
            for (String file : new java.io.File(resource.toURI()).list()) {
                if (file.endsWith(".class")) {
                    String className = packageName + '.' + file.substring(0, file.length() - 6);
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }

    public List<String> getClassN(String directoryPath,String init) {
        List<String> classNames = new ArrayList<>();
        File directory = new File(directoryPath);
        init = init.replace("/", "\\");
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String className = file.getName().replace(".class", "");
                String packagePath = file.getParentFile().getAbsolutePath().replace(init, "").replace("\\", ".");
                String clean = packagePath.replaceFirst(".", "");
                // String packagePath = file.getParentFile().getAbsolutePath().replace(directory.getAbsolutePath(), "").replace(File.separator, ".");
                if (clean.length() == 0) {
                    classNames.add(className);
                }
                else {
                    classNames.add(clean+"."+className);
                }
                // System.out.println(clean+"."+className);
                // classNames.add(packagePath+"."+className);
            } else if (file.isDirectory()) {
                List<String> nestedClassNames = getClassN(file.getAbsolutePath(),init);
                classNames.addAll(nestedClassNames);
            }
        }
        return classNames;
    }

    public List<Class<?>> get_All_Classes(List<String> nom) throws ClassNotFoundException {
        List<Class<?>> valiny = new ArrayList<>();

        for(String n : nom) {
            Class<?> clazz = Class.forName(n);
            valiny.add(clazz);
        }

        return valiny;
    }
    
    public List<Class<?>> getallclass(HttpServlet servlet) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        String config = "WEB-INF/classes";
        String path = servlet.getServletContext().getRealPath(config);
       
        List<String> nomclass = this.getClassN(path, path);
        classes = this.get_All_Classes(nomclass);

        return classes;
    }

    public String getUrl(String context, String Url) throws Exception {
        String valiny = null;

        valiny = Url.replace(context, "");


        return valiny;
    }
}