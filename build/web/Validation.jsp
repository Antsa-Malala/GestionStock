<%
    String idsortie=(String)request.getAttribute("idsortie");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Validation Sortie </title>
        <link rel="stylesheet" href="css/style.css">
        <script src="js/Check.js"></script>
    </head>
    <body>
        <div id="titre">
            <h1>Validation de sortie</h1>
        </div>
        <form action="ValideController" method="post">
            <input type="hidden" value="<%out.print(idsortie);%>" name="idsortie">
            <div>
                <label> Date de sortie
                    <input type="date" name="dateSortie" id="dateSortie">
                </label>
            </div>
            <input type="submit" value="Valider">
            <div id="error"></div>
        </form>
    </body>
</html>
