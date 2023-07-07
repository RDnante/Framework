
# Framework




## Configuration web.xml
configurer votre web.xml comme suit:

```xml
<servlet>
        <servlet-name>FrontServlet</servlet-name>
        <servlet-class>etu1825.framework.servlet.FrontServlet/<servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>FrontServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```
## Présentation classe ModelView
utiliser la classe ModelView pour definir un view, des données, des sessions, obtenir des format Json a envoyer a un view, il a comme argument view (String), data (HashMap<String,Object>), session (HashMap<String,Object>), isjon (Boolean):

```java
    public class ModelView {
    String view;
    HashMap<String,Object> data;
    HashMap<String,Object> session;
    Boolean isJson = false;
    }
```
## passage de données par HashMap
utiliser la classe HashMap pour passer vos données aux attribut request du servlet comme suit :

```java
    HashMap<String,Object> hash = new HashMap<String,Object>();
    hash.put("nom de l'attribut",Object a passer);
```
## utilisation
utiliser setView() pour definir le fichier.jsp viser apres utilisation du fonction, votre fonction retourne la classe ModelView :
```java
    public ModelView votre_fonction() {
        ModelView m = new ModelView();
        m.setView("votre_fichier.jsp");

        return m;
    }
```
utiliser setData() pour definir les données que vous aller passer a votre fichier.jsp a travers une HashMap:
```java
    public ModelView votre_fonction() {
        ModelView m = new ModelView();
        m.setView("votre_fichier.jsp");

        String nom = "Pascal";
        Double age = 20;

        HashMap<String,Object> hash = new HashMap<String,Object>();
        hash.put("nom",nom);
        hash.put("age",age);

        m.setData(hash);
        return m;
    }
```
pour récupérer les Object en jsp utiliser le meme nom d'attribut que vous avez assigner a l'HashMap:
```java
    String nom = (String) request.getAttribute("nom");
    Double age = (String) request.getAttribute("age");
```
pour utiliser des sessions utilser setSession() :
```java
    public ModelView votre_fonction() {
        ModelView m = new ModelView();
        m.setView("votre_fichier.jsp");

        HashMap<String,Object> sess = new HashMap<String,Object>();
        sess.put("nom_session", Object_a_passer);
        m.setSession(sess);
    }
```
pour recupérer les Object dans une session utiliser les meme nom de session que vous avez mis:
```java
    String nom = (String) session.getAttribute("nom_session");
```
uiliser setIsJson() pour transformer vos données dans Data en Json :
```java
    public ModelView votre_fonction() {
        ModelView m = new ModelView();

        m.setIsJson(true);
    }
```
## Annotation
l'annotation @AnnotationMethod a 2 argument : nom et parameters par default "";
utiliser l'annotation @AnnotationMethod pour definir l'url d'appel d'une fonction :
```java
    @AnnotationMethod(nom = "/votre_url")
    public ModelView votre_fonction() {

    }
```
en cas d'une fonction avec arguments ajouter l'annotation parameters dans @AnnotationMethod :
```java
    @AnnotationMethod(nom = "/votre_url" , parameters = "id,nom")
    public ModelView votre_fonction(int id, String nom) {

    }
```
utiliser l'annotation @Auth(profil = "nom_profil") pour determiner le profil qui peut utilser la fonction :
```java
    @Auth(profil = "admin")
    @AnnotationMethod(nom = "/votre_url")
    public ModelView connection() {

    }
```
dans votre web.xml ajouter les parametres ci dessous, cette parametre determine le nom de session des profil que vous aller utiliser
```xml
    <servlet>
    <servlet-name>FrontServlet</servlet-name>
    <servlet-class>etu1825.framework.servlet.FrontServlet</servlet-class>
    <-- modification --> 
    <init-param>
        <param-name>users</param-name>
        <param-value>profil</param-value>
    </init-param>
    <-- modification -->
    </servlet>
```
ajouter un attribut HashMap<String,Object> session; et generer ses setSession() dans votre classe pour recupérer les sessions :
```java
    public class Emp {
    Integer id;
    String nom;
    Integer appel;
    HashMap<String,Object> session;

    public HashMap<String, Object> getSession() {
        return session;
    }
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    }
```
pour declarer un Object comme un singleton utiliser l'annotation @Scope(type = "singleton")
```java 
    @Scope(type = "singleton")
    public class Votre_class {

    }
```
