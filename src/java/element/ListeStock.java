/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package element;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Antsa
 */
public class ListeStock {
    Article article;
    double qi;
    double qr;
    double pum;
    Unite unite;
    double montant;
    Magasin magasin;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public double getQi() {
        return qi;
    }

    public void setQi(double qi) throws Exception{
        
        if(qi>=0)
        {
            this.qi = qi;
        }
        else{
            throw new Exception("La quantite initiale doit etre positive ou nulle");
        }
    }
    
    
    public void setQi(String qi)throws Exception {
        try
        {
            double q=Double.valueOf(qi);
            this.setQi(q);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Quantite initiale invalide");
        }
    }

    public double getQr() {
        return qr;
    }

    public void setQr(double qr) throws Exception{
        
        if(qr>=0)
        {
            this.qr = qr;
        }
        else{
            throw new Exception("La quantite restante doit etre positive ou nulle");
        }
    }
    
    
    public void setQr(String qr)throws Exception {
        try
        {
            double q=Double.valueOf(qr);
            this.setQr(q);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Quantite restante invalide");
        }
    }
    
    

    public double getPum() {
        return pum;
    }

    public void setPum(double pum) throws Exception{
        if(pum>=0)
        {
            this.pum = pum;
        }
        else{
            throw new Exception("Le prix unitaire doit etre positif ou nulle");
        }
    }
    
    public void setPum(String pum)throws Exception {
        try{
            double pu=Double.valueOf(pum);
            this.setPum(pu);
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("Prix unitaire invalide");
        }
        
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant)throws Exception {
        if(montant>=0)
        {
        this.montant = montant;
            
        }
        else{
            throw new Exception("Le montant doit etre positif ou null");
        }
    }
    
      public void setMontant(String montant)throws Exception {
       try
        {
            double montat=Double.valueOf(montant);
            this.setMontant(montat);
            
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("Le montant est invalide");
        }
    }
    

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magaisn) {
        this.magasin = magaisn;
    }

   
    public ListeStock(Article article, double qi, double qr, double pum, Unite unite, double montant, Magasin magasin) throws Exception{
        this.setArticle(article);
        this.setQi(qi);
        this.setQr(qr);
        this.setPum(pum);
        this.setUnite(unite);
        this.setMontant(montant);
        this.setMagasin(magasin);
    }
    
    
    public static ListeStock[] getListeStock(Connection con,Mouvement[] mouvement1,Mouvement[] mouvement2) throws Exception
    {
        ListeStock[] stock=new ListeStock[0];
        ArrayList<ListeStock> liste=new ArrayList<ListeStock>();
        for(int i=0;i<mouvement1.length;i++)
        {
            double montant=mouvement1[i].getArticle().getMontant(con,mouvement1[i].getDate(),mouvement2[i].getDate(),mouvement1[i].getMagasin());
            double pu=0;
            if(montant>0)
            {
                pu=montant/mouvement2[i].getQuantite(); 
            }
            ListeStock ls=new ListeStock(mouvement1[i].getArticle(),mouvement1[i].getQuantite(),mouvement2[i].getQuantite(),pu,mouvement2[i].getUnite(),montant,mouvement1[i].getMagasin());
            liste.add(ls);
        }

        return liste.toArray(stock);
    }
}
