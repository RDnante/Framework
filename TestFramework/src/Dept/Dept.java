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

        return m;
    }

}
