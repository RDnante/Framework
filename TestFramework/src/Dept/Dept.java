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

    public void setNombre(String nombre) {
        int n = Integer.parseInt(nombre);
        Nombre = n;
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
        d[0] = new Dept("jean");
        d[1] = new Dept("pascal");

        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("dept",d);

        m.setData(hash);

        return m;
    }

}
