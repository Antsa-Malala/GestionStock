<%@page import="element.Article"%>
<%@page import="element.Magasin"%>
<%@page import="element.Unite"%>
<%@page import="element.Sortie"%>
<%
    Article[] articles=(Article[])request.getAttribute("articles");
    Magasin[] magasins=(Magasin[])request.getAttribute("magasins");
    Unite[] unites=(Unite[]) request.getAttribute("unites");
    Sortie sortie=(Sortie)request.getAttribute("sortie");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modification Sortie Provisoire</title>
        <link rel="stylesheet" href="css/style.css">
        <script src="js/Check.js"></script>
    </head>
    <body>
        <div id="titre">
            <h1>Modification de sortie Provisoire</h1>
        </div>
        <form action="ModifController" method="post">
            <input type="hidden" value="<%out.print(sortie.getIdMouvement());%>" name="idsortie">
            <div>
                <label> Date de sortie
                    <input type="date" name="dateSortie" id="dateSortie" value="<%out.print(sortie.getDate());%>">
                </label>
            </div>
            <div>
                <label> Article
                    <select name="idarticle" id="idarticle">
                        <%for(int i=0;i<articles.length;i++){
                        if(articles[i].getIdArticle().equals(sortie.getArticle().getIdArticle())){
                        %>
                            <option value="<%out.print(articles[i].getIdArticle());%>" selected ><%out.print(articles[i].getNomArticle());%></option>
                        <% }else{ %>
                            <option value="<%out.print(articles[i].getIdArticle());%>"><%out.print(articles[i].getNomArticle());%></option>
                        <% }
                        } %>
                    </select>
                </label>
            </div>
            <div>
                <label> Magasin
                    <select name="idmagasin" id="idmagasin">
                        <%for(int i=0;i<magasins.length;i++){
                        if(magasins[i].getIdMagasin().equals(sortie.getMagasin().getIdMagasin())){
                        %>
                            <option value="<%out.print(magasins[i].getIdMagasin());%>" selected><%out.print(magasins[i].getNomMagasin());%></option>
                        <% }else{%>
                            <option value="<%out.print(magasins[i].getIdMagasin());%>"><%out.print(magasins[i].getNomMagasin());%></option>
                        <% } 
                        } %>
                    </select>
                </label>
            </div>
             <div>
                <label> Unite
                    <select name="unite" id="unite">
                        <%for(int i=0;i<unites.length;i++){
                        if(unites[i].getNomUnite().equals(sortie.getUnite().getNomUnite())){
                        %>
                            <option value="<%out.print(unites[i].getNomUnite());%>" selected ><%out.print(unites[i].getNomUnite());%></option>
                        <%}else { %>
                            <option value="<%out.print(unites[i].getNomUnite());%>"><%out.print(unites[i].getNomUnite());%></option>
                        <% } 
                        } %>
                    </select>
                </label>
            <div>
                <label> Quantite
                    <input type="text" name="quantite" onchange="check();" id="quantite" value="<%out.println(sortie.getQuantite());%>">
                </label>
            </div>
            </div>
            <input type="submit" value="Valider">
            <div id="error"></div>
        </form>
    </body>
</html>
