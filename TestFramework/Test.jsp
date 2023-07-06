<%@ page import="Dept.Dept" %>
<%
    Dept[] d = null;
    String req = null;
    // attribut sprint 8 7
    int id = 0;
    String nom = null;
    // attribut sprint 10
    int appel = 0;
    String nomclass = null;
    // attribut sprint 9
    String nomfichier = null;
    int taillefichier = 0;
    if (request.getAttribute("dept") != null) {
        d = (Dept[]) request.getAttribute("dept");
    }
    if (request.getAttribute("req") != null) {
        req = (String) request.getAttribute("req");
    }
    else if(request.getAttribute("id") != null && request.getAttribute("nom") != null) {
        id = (Integer) request.getAttribute("id");
        nom = (String) request.getAttribute("nom");
    }
    if (request.getAttribute("appel") != null && request.getAttribute("class") != null) {
        appel = (Integer) request.getAttribute("appel");
        nomclass = (String) request.getAttribute("class");
    }
    if (request.getAttribute("nomfichier") != null && request.getAttribute("taillefichier") != null) {
        nomfichier = (String) request.getAttribute("nomfichier");
        taillefichier = (Integer) request.getAttribute("taillefichier");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    bienvenue presentation
    <ul>
    <% if (d != null) {
    for(Dept de : d) {%>   
            <li><% out.print(de.getNom()); %></li>
    <% }
     } %>
    <% if (req != null) { %>
        <h4><% out.print(req); %></h4>
    <%
     } %>
     <% if (nom != null) { %>
        <h4><% out.print("mon id est"+id+"et mon nom est:"+nom); %></h4>
    <% } %>
    <% if (nomclass != null) { %>
        <h4><% out.print("le class :"+nomclass+" a ete appele "+appel+" fois "); %></h4>
    <% } %>
    <% if (nomfichier != null) { %>
        <h4><% out.print("nom fichier :"+nomfichier+" a comme taille "+taillefichier); %></h4>
    <% } %>
    </ul>  
</body>
</html>