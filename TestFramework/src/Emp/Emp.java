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
    // sprint 12
    HashMap<String,Object> session;

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
    // sprint 12
    public HashMap<String, Object> getSession() {
        return session;
    }
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
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

    // sprint 11
    @Auth(profil = "admin")
    @AnnotationMethod(nom = "/verif")
    public ModelView connection() {
        ModelView v = new ModelView();
        HashMap<String,Object> hs = new HashMap<String,Object>();
        hs.put("sprint11", "autoriser a utiliser la fonction");
        v.setData(hs);

        v.setView("Connecter.jsp");

        return v;
    }

    @Auth(profil = "client")
    @AnnotationMethod(nom = "/verifclient")
    public ModelView conn() {
        ModelView v = new ModelView();
        HashMap<String,Object> hs = new HashMap<String,Object>();
        hs.put("sprint11", "autoriser a utiliser la fonction");
        v.setData(hs);

        v.setView("Connecter.jsp");

        return v;
    }

    // sprint12
    @AnnotationMethod(nom = "/printsession")
    public ModelView printsession() {
        ModelView view = new ModelView();
        HashMap<String,Object> data = new HashMap<String,Object>();

        this.setNom("emp session");

        data.put("emp", this);

        view.setData(data);

        view.setView("Sprint12.jsp");

        return view;
    }

    // sprint 13
    @AnnotationMethod(nom = "/json")
    public ModelView jsonMethod() {
        ModelView view = new ModelView();
        HashMap<String,Object> data = new HashMap<String,Object>();
        data.put("name_json", "nom");

        view.setIsJson(true);

        view.setData(data);

        return view;
    }
}
