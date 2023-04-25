package etu1825.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etu1825.framework.AnnotationMethod;
import etu1825.framework.Mapping;
import etu1825.framework.ModelView;
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

            for (String key : MappingUrls.keySet()) {
                Mapping value = MappingUrls.get(key);
                out.println(key + " : " + value.getClassName() +"  "+ value.getMethod());
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Util u = new Util();
        PrintWriter out = response.getWriter();

        String url = request.getRequestURI().toString();

        out.println("url"+url);
        out.println("context"+request.getContextPath());

        try {
            this.init(out);
            String met = u.getUrl(request.getContextPath(), url);
            out.println("lien"+met);
            Mapping map = MappingUrls.get(met);

            if (map == null) {
                throw new Exception("tsy misy mapping");
            }

            out.println("misy");

            Class<?> c = Class.forName(map.getClassName());
            Object o = c.getDeclaredConstructor().newInstance();
            ModelView mv = (ModelView) o.getClass().getMethod(map.getMethod()).invoke(o);

            RequestDispatcher dispatcher = request.getRequestDispatcher(mv.getView());
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
    
}