<%@page import="element.Sortie"%>
<%
   Sortie[] sortie=(Sortie[])request.getAttribute("sorties");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sorties Non-validées</title>
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
                    <th>Magasin</th>  
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <%for(int i=0;i<sortie.length;i++){%>
                    <tr>
                        <td><%out.println(sortie[i].getDate());%></td>
                        <td><%out.println(sortie[i].getArticle().getNomArticle());%></td>
                        <td><%out.println(sortie[i].getQuantite());%></td>
                        <td><%out.println(sortie[i].getUnite().getNomUnite());%></td>
                        <td><%out.println(sortie[i].getMagasin().getNomMagasin());%></td>
                        <td><a href="ModificationController?idsortie=<%out.println(sortie[i].getIdMouvement());%>">Modifier</a></td>
                        <td><a href="ValidationController?idsortie=<%out.println(sortie[i].getIdMouvement());%>">Valider</a></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
