<%@page import="element.Article"%>
<%@page import="element.Magasin"%>
<%@page import="element.Unite"%>
<%
    Article[] articles=(Article[])request.getAttribute("articles");
    Magasin[] magasins=(Magasin[])request.getAttribute("magasins");
    Unite[] unites=(Unite[]) request.getAttribute("unites");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insertion Entree</title>
        <link rel="stylesheet" href="css/style.css">
        <script src="js/Check.js"></script>
    </head>
    <body>
        <div id="titre">
            <h1>Entree de marchandise</h1>
        </div>
        <form action="InsertionEntreeController" method="post">
            <div>
                <label> Date d'entrée
                    <input type="date" name="dateEntree" id="dateEntree">
                </label>
            </div>
            <div>
                <label> Article
                    <select name="idarticle" id="idarticle">
                        <%for(int i=0;i<articles.length;i++){%>
                            <option value="<%out.print(articles[i].getIdArticle());%>"><%out.print(articles[i].getNomArticle());%></option>
                        <% } %>
                    </select>
                </label>
            </div>
            <div>
                <label> Magasin
                    <select name="idmagasin" id="idmagasin">
                        <%for(int i=0;i<magasins.length;i++){%>
                            <option value="<%out.print(magasins[i].getIdMagasin());%>"><%out.print(magasins[i].getNomMagasin());%></option>
                        <% } %>
                    </select>
                </label>
            </div>
             <div>
                <label> Prix Unitaire
                    <input type="text" name="pu" id="pu">
                </label>
            </div>
             <div>
                <label> Unite
                    <select name="unite" id="unite">
                        <%for(int i=0;i<unites.length;i++){%>
                            <option value="<%out.print(unites[i].getNomUnite());%>"><%out.print(unites[i].getNomUnite());%></option>
                        <% } %>
                    </select>
                </label>
            <div>
                <label> Quantite
                    <input type="text" name="quantite" onchange="check();" id="quantite">
                </label>
            </div>
            </div>
            <input type="submit" value="Valider">
            <div id="error"></div>
        </form>
    </body>
</html>
