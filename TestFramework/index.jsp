<%@ page import="Dept.Dept" %>
<%
    //Dept[] d = (Dept[]) request.getAttribute("dept");
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
    <%-- <ul>
    <%// for(Dept de : d) {%>   
            <li><% //out.print(de.getNom()); %></li>
    <% //} %>
    </ul> --%>
    <form action="./save" method="post">
        <input type="text" name="Nom" id="">
        <input type="number" name="Id" id="">
        <input type="submit" value="ok">
    </form> 

    <h4>se connecter</h4>
    <form action="./login" method="post">
        <input type="text" name="nom">
        <input type="submit" value="se connecter">
    </form>
</body>
</html>