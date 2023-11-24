
import connection.Connexion;
import element.Article;
import element.Magasin;
import java.sql.Connection;
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Antsa
 */
public class Main {

    public static void main(String args[]) {
        try {
          Connection con=Connexion.getConnect("postgres");
         /*double montant=Article.getMontant(con,Date.valueOf("2023-01-01"), Date.valueOf("2023-01-03"), Article.getById("SRL1100", con), Magasin.getById("MGS0003",con));
         System.out.println(montant);*/
          con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
