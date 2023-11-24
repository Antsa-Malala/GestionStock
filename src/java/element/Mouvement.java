/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package element;

import connection.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Antsa
 */
public class Mouvement {
    int idMouvement;
    Date date;
    Article article;
    double quantite;
    double pu;
    Magasin magasin;
    Unite unite;
    Date dateValidation;
    String mouvement;

    public String getMouvement() {
        return mouvement;
    }

    public void setMouvement(String mouvement) {
        this.mouvement = mouvement;
    }

    
    public int getIdMouvement() {
        return idMouvement;
    }

    public void setIdMouvement(int idMouvement)throws Exception {
        if(idMouvement<=0)
        {
            throw new Exception("id mouvement doit etre positif");
        }
        else{
            this.idMouvement = idMouvement;
        }
    }
    
    public void setIdMouvement(String idMouvement) throws Exception{
        try{
            int id=Integer.valueOf(idMouvement);
            this.setIdMouvement(id);
        }
        catch(Exception e)
        {
            throw new Exception("Id mouvement invalide");
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) throws Exception{
        if(date==null)
        {
            throw new Exception("La date ne peut pas etre null");
        }else{
            this.date = date;
        }
    }
    
    public void setDate(String date) throws Exception
    {
        if(date.equals(""))
        {
            throw new Exception("La date ne peut pas etre null");
        }else{
            Date daty=Date.valueOf(date);
            this.setDate(daty);
        }
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) throws Exception{
        if(quantite>=0)
        {
            this.quantite = quantite;
        }
        else{
            throw new Exception("La quantite doit etre positive ou nulle");
        }
    }
    
     public void setQuantite(String quantite) throws Exception{
        try
        {
           double qtt=Double.valueOf(quantite);
           this.setQuantite(qtt);
        }
        catch(Exception e){
            throw new Exception("quantite invalide");
        }
    }

    public double getPu() {
        return pu;
    }
    public void setPu(double pu) throws Exception{
        if(pu>=0)
        {
            this.pu = pu;
        }
        else{
            throw new Exception("Le prix unitaire doit etre positif ou nulle");
        }
    }
    
     public void setPu(String pu) throws Exception{
        try
        {
           double p=Double.valueOf(pu);
           this.setPu(p);
        }
        catch(Exception e){
            throw new Exception("prix unitaire invalide");
        }
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) throws Exception{
       this.unite=unite;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date date) throws Exception{
        if(date==null)
        {
            throw new Exception("La date de validation ne peut pas etre null");
        }else{
            this.dateValidation = date;
        }
    }
    
    public void setDateValidation(String date) throws Exception
    {
        if(date.equals(""))
        {
            throw new Exception("La date de validation ne peut pas etre null");
        }else{
            Date daty=Date.valueOf(date);
            this.setDateValidation(daty);
        }
    }
    
    
     
    public Mouvement(Connection c,String date, String idarticle, String quantite, String idmagasin,String idunite) throws Exception{
        this.setDate(date);
        Article a=Article.getById(idarticle,c);
        this.setArticle(a);
        this.setQuantite(quantite);
        Magasin m=Magasin.getById(idmagasin,c);
        this.setMagasin(m);
        Unite u=Unite.getById(idunite,c);
        this.setUnite(u);
    }
    
     public Mouvement(Connection c,String idmouvement,String date, String idarticle, String quantite, String idmagasin,String idunite) throws Exception{
        this.setDate(date);
        this.setIdMouvement(idmouvement);
        Article a=Article.getById(idarticle,c);
        this.setArticle(a);
        this.setQuantite(quantite);
        Magasin m=Magasin.getById(idmagasin,c);
        this.setMagasin(m);
        Unite u=Unite.getById(idunite,c);
        this.setUnite(u);
    }
     
      public Mouvement(Connection c,String date, String idarticle, String quantite,String pu, String idmagasin,String idunite,double huhu) throws Exception{
        this.setDate(date);
        Article a=Article.getById(idarticle,c);
        this.setArticle(a);
        this.setQuantite(quantite);
        Magasin m=Magasin.getById(idmagasin,c);
        this.setMagasin(m);
        Unite u=Unite.getById(idunite,c);
        this.setUnite(u);
        this.setPu(pu);
    }
    
    public Mouvement()
    {
        
    }

     public Mouvement(Connection c,int identree,Date date,String idarticle,double quantite,int idunite,double pu,String idmagasin) throws Exception{
        this.setIdMouvement(identree);
        this.setDate(date);
        Article a=Article.getById(idarticle,c);
        this.setArticle(a);
        this.setQuantite(quantite);
        Unite u=Unite.getById(String.valueOf(idunite),c);
        this.setUnite(u);
        this.setPu(pu);
        Magasin m=Magasin.getById(idmagasin,c);
        this.setMagasin(m);
     }
      public Mouvement(Connection c,int identree,Date date,String idarticle,double quantite,int idunite,double pu,String idmagasin,String mouvement,double haha) throws Exception{
        this.setIdMouvement(identree);
        this.setDate(date);
        Article a=Article.getById(idarticle,c);
        this.setArticle(a);
        this.setQuantite(quantite);
        Unite u=Unite.getById(String.valueOf(idunite),c);
        this.setUnite(u);
        this.setPu(pu);
        Magasin m=Magasin.getById(idmagasin,c);
        this.setMagasin(m);
        this.setMouvement(mouvement);
     }
     
      public Mouvement(Connection c,int identree,Date date,String idarticle,double quantite,int idunite,double pu,String idmagasin,Date datevalidation) throws Exception{
        this.setIdMouvement(identree);
        this.setDate(date);
        Article a=Article.getById(idarticle,c);
        this.setArticle(a);
        this.setQuantite(quantite);
        Unite u=Unite.getById(String.valueOf(idunite),c);
        this.setUnite(u);
        this.setPu(pu);
        Magasin m=Magasin.getById(idmagasin,c);
        this.setMagasin(m);
        this.setDateValidation(datevalidation);
     }
     
    public Mouvement(Date daty,Article a,double qtt,Unite unite,double pu,Magasin maga)throws Exception{
        this.setDate(daty);
        this.setArticle(a);
        this.setQuantite(qtt);
        this.setUnite(unite);
        this.setPu(pu);
        this.setMagasin(maga);
    }
    
    public static double getMontant(Mouvement[] entrees) throws Exception{
         double montant=0;
         for(int i=0;i<entrees.length;i++)
         {
             montant+=entrees[i].getPu()*entrees[i].getQuantite();
         }
         return montant;
    }
    
    public static Mouvement[] getAllMouvement(Connection con) throws Exception{
        Mouvement[] mouvement=new Mouvement[0];
        ArrayList<Mouvement> move=new ArrayList<Mouvement>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from mouvementinvalide";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Mouvement m=new Mouvement(con,res.getInt("idMouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),res.getDouble("pu"),res.getString("idMagasin"));
                move.add(m);
            }
        }
        catch(Exception ex){
            throw ex;
        }
        finally{

            if( res != null ){
                res.close();
            }
            if(st != null ){
                st.close();
            }
            if(co==1)
            {
                con.close();
            }
        } 
        return move.toArray(mouvement);
    }
    
    
    public static Mouvement[] getMouvementInsere(Connection con) throws Exception
    {
        Mouvement[] sor=new Mouvement[0];
        ArrayList<Mouvement> sort=new ArrayList<Mouvement>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from mouvementinsere";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Mouvement a=new Mouvement(con,res.getInt("idmouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),res.getDouble("pu"),res.getString("idmagasin"),res.getString("mouvement"),1);
                sort.add(a);
            }
        }
        catch(Exception ex){
            throw ex;
        }
        finally{

            if( res != null ){
                res.close();
            }
            if(st != null ){
                st.close();
            }
            if(co==1)
            {
                con.close();
            }
        }
        return sort.toArray(sor);
    }
}
