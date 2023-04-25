package etu1825.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etu1825.framework.AnnotationMethod;
import etu1825.framework.Mapping;
import utils.*;;

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls = new HashMap<String, Mapping>();

    public void init(PrintWriter out) throws Exception {
        try {
            Util u = new Util();

            List<Class<?>> classes = u.getallclass(this);

            for(Class<?> c : classes) {
                for(Method method : c.getMethods()) {
                    AnnotationMethod ann = method.getAnnotation(AnnotationMethod.class);
                    if (ann == null) continue;

                    Mapping M = new Mapping(c.getName(),method.getName());
                    String lien = ann.nom();

                    MappingUrls.put(lien,M);
                }
            }

            // for (String key : MappingUrls.keySet()) {
            //     Mapping value = MappingUrls.get(key);
            //     out.println(key + " : " + value.getClassName() +"  "+ value.getMethod());
            // }

        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Util u = new Util();

        PrintWriter out = response.getWriter();
        try {
            this.init(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}