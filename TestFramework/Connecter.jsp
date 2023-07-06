<% 
    HttpSession sess = request.getSession(); 
    String nom = (String) sess.getAttribute("users");
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
</body>
</html>