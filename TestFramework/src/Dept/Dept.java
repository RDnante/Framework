package Dept;

import java.util.HashMap;

import etu1825.framework.AnnotationMethod;
import etu1825.framework.ModelView;

public class Dept {
    String Nom;
    int Nombre;

    public int getNombre() {
        return Nombre;
    }

    public void setNombre(int nombre) {
        Nombre = nombre;
    }

    public Dept() {
    }

    public Dept(String nom) {
        this.Nom = nom;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    @AnnotationMethod(nom = "/alefa")
    public ModelView nom() {
        ModelView m = new ModelView();
        m.setView("Test.jsp");

        Dept[] d = new Dept[2];
        d[0] = new Dept("petite");
        d[1] = new Dept("pascal");

        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("dept",d);

        m.setData(hash);

        return m;
    }

    // sprint 7
    @AnnotationMethod(nom = "/save")
    public ModelView save() {
        ModelView m = new ModelView();
        m.setView("Test.jsp");

        String req = "insert into "+this.getNom()+" and "+this.getNombre();
        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("req", req);

        m.setData(hash);
        
        return m;
    }

    // sprint 8
    @AnnotationMethod(nom = "/param" , parameters = "id,nom")
    public ModelView Test_parameter(int id, String nom) {
        ModelView m = new ModelView();
        m.setView("Test.jsp");

        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("id", id);
        hash.put("nom", nom);

        m.setData(hash);

        return m;
    }

    @AnnotationMethod(nom="/fichier", parameters = "file")
    public ModelView fichier() {
        ModelView m = new ModelView();
        m.setView("Test.jsp");

        return m;
    }

}
