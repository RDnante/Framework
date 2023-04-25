package emp;

import etu1825.framework.AnnotationMethod;

public class Emp {
    String Nom;
    int Age;


    public Emp() {
    }

    public Emp(String nom, int age) {
        this.setAge(age);
        this.setNom(nom);
    }

    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        Nom = nom;
    }
    public int getAge() {
        return Age;
    }
    public void setAge(int age) {
        Age = age;
    }
}
