<%@ page import="Dept.Dept" %>
<%
    Dept[] d = null;
    String req = null;
    int id = 0;
    String nom = null;
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
    <%
     } %>
    </ul>  
</body>
</html>