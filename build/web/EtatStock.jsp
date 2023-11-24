<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Etat Stock</title>
         <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div id="titre">
            <h1>Consulter etat de stock</h1>
        </div>
        <form action="EtatStockController" method="post">
            <div>
                <label>
                    Date 1
                    <input type="date" name="date1" id="date1">
                </label>
            </div>
            <div>
                <label>
                    Date 2
                    <input type="date" name="date2" id="date2">
                </label>
            </div>
            <div>
                <label>
                    Article
                    <input type="text" name="article" id="article">
                </label>
            </div>
            <div>
                <label> Magasin
                    <input type="text" name="magasin" id="magasin">
                </label>
            </div>
            <input type="submit" value="Consulter">
        </form>
    </body>
</html>
