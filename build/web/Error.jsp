<%
    Exception e=(Exception) request.getAttribute("error");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erreur!</title>
         <link rel="stylesheet" href="css/error.css">
    </head>
    <body>
        <div class="error-container">
            <h1><% out.print(e.getMessage());%></h1>
        </div>
    </body>
</html>
