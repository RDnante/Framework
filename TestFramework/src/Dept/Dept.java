package Dept;

import java.util.HashMap;

import etu1825.framework.AnnotationMethod;
import etu1825.framework.ModelView;

public class Dept {
    String Nom;
    int Id;
    int appel;

    public int getAppel() {
        return appel;
    }

    public void setAppel(int appel) {
        this.appel = appel;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public Dept() {
        this.setAppel(0);
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

        String req = "insert into "+this.getNom()+" and "+this.getId();
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

    @AnnotationMethod(nom="/fichier")
    public ModelView fichier() {
        ModelView m = new ModelView();

        return m;
    }

    // sprint 10
    @AnnotationMethod(nom = "/singDept")
    public ModelView hala() {
        this.plus();
        ModelView mo = new ModelView();
        
        HashMap<String,Object> m = new HashMap<String,Object>();
        m.put("appel", this.getAppel());
        m.put("class", this.getClass().getSimpleName());
        mo.setData(m);
        mo.setView("Test.jsp");

        return mo;
    }

    public void plus() {
        Integer test = this.getAppel();
        this.setAppel(test+1);
    }

    @AnnotationMethod(nom = "/login",parameters = "nom")
    public ModelView login(String nom) {
        ModelView test = new ModelView();

        HashMap<String,Object> sess = new HashMap<String,Object>();
        sess.put("users", nom);
        test.setSession(sess);

        test.setView("Connecter.jsp");

        return test;
    }

}
