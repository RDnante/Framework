package Dept;

import java.util.HashMap;

import etu1825.framework.AnnotationMethod;

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
    public void test() {

    }


    public String save() {
        String req = "insert into "+this.getNom()+" and "+this.getNombre();

        return req;
    }
}
