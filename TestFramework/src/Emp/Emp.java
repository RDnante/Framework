package Emp;

import java.util.HashMap;

import etu1825.framework.AnnotationMethod;
import etu1825.framework.Auth;
import etu1825.framework.ModelView;
import etu1825.framework.Scope;

@Scope(type = "singleton")
public class Emp {
    Integer id;
    String nom;
    Integer appel;
    public Integer getAppel() {
        return appel;
    }
    public void setAppel(Integer appel) {
        this.appel = appel;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Emp() {
        this.setAppel(0);
    }

    // sprint 10
    @AnnotationMethod(nom = "/singEmp")
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

    @Auth(profil = "user")
    @AnnotationMethod(nom = "/verif")
    public ModelView connection() {
        ModelView v = new ModelView();
        HashMap<String,Object> hs = new HashMap<String,Object>();

        v.setView("Connecter.jsp");

        return v;
    }
}
