<% 
    HttpSession sess = request.getSession(); 
    String nom = (String) sess.getAttribute("profil");

    String hafatra = null;
    if (request.getAttribute("sprint11") != null) {
        hafatra = (String) request.getAttribute("sprint11");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connecter</title>
</head>
<body>
    <h1>bienvenue :<% out.print(nom); %></h1>
    <a href="./verif">lien pour acceder au fonction</a>
    <% if(hafatra != null) { %>
        <p><% out.print(hafatra); %></p>
    <% } %>
</body>
</html>