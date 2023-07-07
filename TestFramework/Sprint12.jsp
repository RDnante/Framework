<%@ page import="java.util.HashMap" %>
<%@ page import="Emp.Emp" %>
<%
    Emp e = (Emp) request.getAttribute("emp");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sprint 12</title>
</head>
<body>
    <h1>class <% out.print(e.getNom()); %></h1>
    <p>nom : <% out.print(e.getSession().get("sprint12")); %></p>
</body>
</html>