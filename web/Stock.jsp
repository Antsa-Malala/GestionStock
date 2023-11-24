<%@page import="element.EtatStock"%>
<%@page import="element.ListeStock"%>
<%
   EtatStock etat=(EtatStock)request.getAttribute("EtatStock");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Etat de Stock</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <h1>Etat de stock entre le <% out.print(etat.getDate1());%> et le <% out.print(etat.getDate2());%></h1>
        <h3>Estimation : <%out.print(etat.getMontant());%> Ar</h3>
        <table>
            <thead>
                <tr>
                    <th>Id Article</th>
                    <th>Article</th>
                    <th>Qte Initiale</th>
                    <th>Qte Restante</th>
                    <th>Unite</th>
                    <th>Prix Unitaire</th>
                    <th>Montant(Ar)</th>
                    <th>Magasin</th>   
                </tr>
            </thead>
            <tbody>
                <%
                    ListeStock[] liste=etat.getListeStock();
                    for(int i=0;i<liste.length;i++){%>
                        <tr>
                            <td><%out.println(liste[i].getArticle().getIdArticle());%></td>
                            <td><%out.println(liste[i].getArticle().getNomArticle());%></td>
                            <td><%out.println(liste[i].getQi());%></td>
                            <td><%out.println(liste[i].getQr());%></td>
                            <td><%out.println(liste[i].getUnite().getNomUnite());%></td>
                            <td><%out.println(liste[i].getPum());%></td>
                            <td><%out.println(liste[i].getMontant());%></td>
                            <td><%out.println(liste[i].getMagasin().getNomMagasin());%></td>
                        </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>
