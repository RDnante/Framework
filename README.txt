creer un web xml contenant :
	<servlet>
        <servlet-name>FrontServlet</servlet-name>
        <servlet-class>etu1825.framework.servlet.FrontServlet</servlet-class>
    	</servlet>
  
    	<servlet-mapping>
        <servlet-name>FrontServlet</servlet-name>
        <url-pattern>/</url-pattern>
    	</servlet-mapping>

- annotation :
	creer une fonction pour passer les donnee (type de retour = Modelview)
	utiliser l'annotation @AnnotationMethod(nom = "/votre url") pour passer des donner dans un view
	utiliser la classe Modelview pour passer les donner :	
		Modelview.setView("votreview.jsp")
		Modelview.setData(vosDonnee)

- inserer des donnee des formulaire:
	les "name" des inputs dans l'url doivent avoir les meme noms que celles dans votre classe
	avoir le fonction save() ayant comme requete :
	"insert into votretable values("+this.getvotreAttribut()+","+this.getvotreattribut+")"

jar cvf mineframe.jar *
copy "mineframe.jar" "C:\Users\nante\Documents\GitHub\Framework\TestFramework\WEB-INF\lib"
	

	
	

