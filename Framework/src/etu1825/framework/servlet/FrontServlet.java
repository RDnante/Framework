package etu1825.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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
    private static Util u = new Util();

    public void init(PrintWriter out) throws Exception {
        try {
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
        this.processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String url = request.getRequestURI().toString();

        // out.println("url"+url);
        // out.println("context"+request.getContextPath());

        try {
            this.init(out);
            String met = u.getUrl(request.getContextPath(), url);
            out.println("lien"+met);
            Mapping map = MappingUrls.get(met);

            if (map == null) {
                throw new Exception("tsy misy mapping");
            }

            Class<?> c = Class.forName(map.getClassName());
            Object o = c.getDeclaredConstructor().newInstance();

            // prend la methode correspondant a l'appel dans l'url
            Method[] list_m = o.getClass().getDeclaredMethods();
            Method m = null;
            for(Method mi : list_m) {
                if (map.getMethod().equals(mi.getName())) {
                    m = mi;
                    break;
                }
            }

            // sprint 7
            Enumeration<String> v =  request.getParameterNames();

            if (m.getAnnotation(AnnotationMethod.class).parameters().equals("")) {
                while(v.hasMoreElements()) {
                    String nom = v.nextElement();
                    Field field = o.getClass().getDeclaredField(nom);
                    if (field == null) {
                        continue;
                    }
    
                    Object value = null;
                    Class<?> parameterType = o.getClass().getDeclaredMethod("set" + nom , field.getType()).getParameterTypes()[0];
    
                    // cast un objet en un Objet equivalent au demande
                    value = u.cast_Object(parameterType, request, nom);
    
                    o.getClass().getDeclaredMethod("set"+nom, parameterType).invoke(o,value);
                }
            }
            // fin sprint 7

            // sprint 8
            ArrayList<Class<?>> parameter_types = new ArrayList<Class<?>>();

            Class<?>[] param_class = m.getParameterTypes();
            ArrayList<Object> value = new ArrayList<>();
            if (param_class.length != 0) {
                parameter_types.addAll(new ArrayList<>(Arrays.asList(param_class)));
                String[] p = m.getAnnotation(AnnotationMethod.class).parameters().split(",");
                for (int i = 0; i<param_class.length; i++) {
                    value.add(u.cast_Object(param_class[i], request, p[i]));
                }
            }
            // fin Sprint 8
            
            ModelView mv = (ModelView) o.getClass().getMethod(map.getMethod(),param_class).invoke(o, value.toArray());

            HashMap<String,Object> data = mv.getData();

            if (data != null) {
                for(String key : data.keySet()) {
                    Object donnee = data.get(key);
                    request.setAttribute(key, donnee);
                }
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(mv.getView());
            dispatcher.forward(request, response);

        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
    
}