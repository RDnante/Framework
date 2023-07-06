package etu1825.framework;

import java.util.HashMap;

public class ModelView {
    String view;
    HashMap<String,Object> data;
    HashMap<String,Object> session;

    public ModelView() {
    }

    public ModelView(String view) {
        this.setView(view);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    
    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
}
