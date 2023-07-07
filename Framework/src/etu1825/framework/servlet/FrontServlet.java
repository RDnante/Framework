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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import etu1825.framework.AnnotationMethod;
import etu1825.framework.Auth;
import etu1825.framework.FileUpload;
import etu1825.framework.Mapping;
import etu1825.framework.ModelView;
import etu1825.framework.Scope;
import utils.*;

@MultipartConfig
public class FrontServlet extends HttpServlet {
    //sprint 7
    HashMap<String,Mapping> MappingUrls = new HashMap<String, Mapping>();
    // sprint 10
    HashMap<Class<?>,Object> MappingSingleton = new HashMap<Class<?>, Object>();
    private static Util u = new Util();
    //sprint11
    private String user_session_name;
    //sprint12
    private HashMap<String,Object> MappingSession = new HashMap<String,Object>();


    

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        super.init(config);

        this.user_session_name = config.getInitParameter("users");
    }

    @Override
    public void init() {
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
                }
            }

            // for (String key : MappingUrls.keySet()) {
            //     Mapping value = MappingUrls.get(key);
            //     out.println(key + " : " + value.getClassName() +"  "+ value.getMethod());
            // }

        } catch (Exception e) {
            e.printStackTrace();
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
                System.out.println("sprint 10 singleton");
            }
            else {
                o = c.getDeclaredConstructor().newInstance();
                System.out.println("sprint 10 non singleton");
            }

            // sprint 12
            Class<?>[] hash_session_class = new Class[1];
            hash_session_class[0] = HashMap.class;

            try {
                Method setsession = o.getClass().getMethod("setSession", hash_session_class);
                setsession.invoke(o, this.MappingSession);
                System.out.println("misy ilay method");
            } catch (Exception e) {
                System.out.println("tsy misy ilay method");
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

            
            //ArrayList<Class<?>> parameter_types = new ArrayList<Class<?>>();

            ModelView mv = null;

            Class<?>[] param_class = m.getParameterTypes();
            ArrayList<Object> value = new ArrayList<>();

            // sprint 11
            if (m.getAnnotation(Auth.class) != null) {
                if (session.getAttribute(user_session_name) == null) throw new Exception("connecter vous pour acceder au fonction ="+m.getName());
                System.out.println("sprint 11");

                Auth auth = m.getAnnotation(Auth.class);
                String users = (String) session.getAttribute(user_session_name);
                String profil = auth.profil();
                if (profil.equals(users)) {
                    // sprint 8
                    if (param_class.length != 0) {

                        //parameter_types.addAll(new ArrayList<>(Arrays.asList(param_class)));
                        String[] p = m.getAnnotation(AnnotationMethod.class).parameters().split(",");
                        for (int i = 0; i<param_class.length; i++) {
                            value.add(u.cast_Object(param_class[i], request, p[i]));
                        }
                        mv = (ModelView) o.getClass().getMethod(map.getMethod(),param_class).invoke(o, value.toArray());
                        System.out.println("sprint 8");
                    }
                    else {
                        mv = (ModelView) o.getClass().getMethod(map.getMethod()).invoke(o);
                        System.out.println("sprint 7");
                    }
                    // fin Sprint 8
                }
                else {
                    throw new Exception("le profile ="+users+"n'est pas autoriser a appeler la fonction ="+m.getName());
                }
            }
            else {
                // sprint 8
                    if (param_class.length != 0) {
                        //parameter_types.addAll(new ArrayList<>(Arrays.asList(param_class)));
                        String[] p = m.getAnnotation(AnnotationMethod.class).parameters().split(",");
                        for (int i = 0; i<param_class.length; i++) {
                            value.add(u.cast_Object(param_class[i], request, p[i]));
                        }
                        mv = (ModelView) o.getClass().getMethod(map.getMethod(),param_class).invoke(o, value.toArray());
                        System.out.println("sprint 8");
                    }
                    else {
                        mv = (ModelView) o.getClass().getMethod(map.getMethod()).invoke(o);
                        System.out.println("sprint 7");
                    }
                    // fin Sprint 8
            }
            // sprint 11
            
            String contenttype = request.getContentType();
            // sprint 9 upload fichier
            if(contenttype != null && contenttype.toLowerCase().startsWith("multipart/form-data")) {
                System.out.println("mandalo sprint 9");

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


            // sprint 13

            HashMap<String,Object> data = mv.getData();

            if (mv.getIsJson().equals(true)) {
                System.out.println("is json");
                Gson json = new Gson();
                out.print("transformation json");
                out.print(json.toJson(data));
            }

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
                    // sprint 12
                    this.MappingSession.put(key, sess);
                }
            }

            if(mv.getView() != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(mv.getView());
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            out.println(e.getMessage()+" cause ="+e.getCause());
        }
    }
    
}