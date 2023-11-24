<%@page import="element.Mouvement"%>
<%
   Mouvement[] sortie=(Mouvement[])request.getAttribute("mouvement");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mouvements Inserées</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <h1>Sortie de marchandises non-validées</h1>
        <table>
            <thead>
                <tr>
                    <th>Date </th>
                    <th>Article</th>
                    <th>Quantité</th>   
                    <th>Unite</th>
                    <th>Prix unitaire</th>
                    <th>Magasin</th>    
                    <th>Mouvement</th>    
                </tr>
            </thead>
            <tbody>
                <%for(int i=0;i<sortie.length;i++){%>
                    <tr>
                        <td><%out.println(sortie[i].getDate());%></td>
                        <td><%out.println(sortie[i].getArticle().getNomArticle());%></td>
                        <td><%out.println(sortie[i].getQuantite());%></td>
                        <td><%out.println(sortie[i].getUnite().getNomUnite());%></td>
                        <td><%out.println(sortie[i].getPu());%></td>
                        <td><%out.println(sortie[i].getMagasin().getNomMagasin());%></td>
                        <td><%out.println(sortie[i].getMouvement());%></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>