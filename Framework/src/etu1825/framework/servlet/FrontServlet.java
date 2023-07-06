package etu1825.framework.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import etu1825.framework.AnnotationMethod;
import etu1825.framework.FileUpload;
import etu1825.framework.Mapping;
import etu1825.framework.ModelView;
import etu1825.framework.Scope;
import utils.*;

@MultipartConfig
public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls = new HashMap<String, Mapping>();
    HashMap<Class<?>,Object> MappingSingleton = new HashMap<Class<?>, Object>();
    private static Util u = new Util();

    public void init(PrintWriter out) throws Exception {
        try {
            List<Class<?>> classes = u.getallclass(this);

            // sprint 6 angamba
            for(Class<?> c : classes) {
                for(Method method : c.getMethods()) {
                    AnnotationMethod ann = method.getAnnotation(AnnotationMethod.class);
                    if (ann == null) continue;

                    Mapping M = new Mapping(c.getName(),method.getName());
                    String lien = ann.nom();

                    MappingUrls.put(lien,M);
                }
            }

            // sprint 10 singleton
            for(Class<?> c : classes) {
                if(c.isAnnotationPresent(Scope.class)) {
                    if (MappingSingleton.get(c) == null) {
                        MappingSingleton.put(c, c.getDeclaredConstructor().newInstance());
                    }
                    out.println(c.getSimpleName());
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

        HttpSession session = request.getSession();

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
            Object o = null;

            // sprint 10 et sprint 8 (resaka instance)
            if (MappingSingleton.get(c) != null) {
                o = MappingSingleton.get(c);
            }
            else {
                o = c.getDeclaredConstructor().newInstance();
            }

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

            ModelView mv = null;

            Class<?>[] param_class = m.getParameterTypes();
            ArrayList<Object> value = new ArrayList<>();
            if (param_class.length != 0) {
                parameter_types.addAll(new ArrayList<>(Arrays.asList(param_class)));
                String[] p = m.getAnnotation(AnnotationMethod.class).parameters().split(",");
                for (int i = 0; i<param_class.length; i++) {
                    value.add(u.cast_Object(param_class[i], request, p[i]));
                }
                mv = (ModelView) o.getClass().getMethod(map.getMethod(),param_class).invoke(o, value.toArray());
            }
            else {
                mv = (ModelView) o.getClass().getMethod(map.getMethod()).invoke(o);
            }
            // fin Sprint 8
            
            String contenttype = request.getContentType();
            System.out.println("mandalo 10");
            // sprint 9 upload fichier
            if(contenttype != null && contenttype.toLowerCase().startsWith("multipart/form-data")) {
                FileUpload file = new FileUpload();
                for(Part part : request.getParts()) {
                    try {
                        file.setName(Paths.get(part.getSubmittedFileName()).getFileName().toString());
                        InputStream in = part.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int byteread;
                        while((byteread = in.read()) != -1) {
                            baos.write(buffer, 0, byteread);
                        }
                        file.setBytearray(baos.toByteArray());
                        out.println("taile byte :"+file.getBytearray().length);
                        mv = new ModelView();
                        HashMap<String,Object> mas = new HashMap<String,Object>();
                        mas.put("taillefichier", file.getBytearray().length);
                        mas.put("nomfichier", file.getName());

                        mv.setData(mas);
                        mv.setView("Test.jsp");
                        } catch (Exception e) {
                            out.println(e.getMessage()+"cause"+e.getCause());
                        }
                    }
            }
            // fin sprint 9
            HashMap<String,Object> data = mv.getData();

            if (data != null) {
                for(String key : data.keySet()) {
                    Object donnee = data.get(key);
                    request.setAttribute(key, donnee);
                }
            }

            // sprint 11 maka ny session rehetra
            HashMap<String,Object> sessi = mv.getSession();
            
            if(sessi != null) {
                for(String key : sessi.keySet()) {
                    Object sess = sessi.get(key);
                    session.setAttribute(key, sess);
                }
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(mv.getView());
            dispatcher.forward(request, response);

        } catch (Exception e) {
            out.println(e.getMessage()+e.getCause());
        }
    }
    
}