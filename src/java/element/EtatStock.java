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

/**
 *
 * @author Antsa
 */
public class EtatStock {
    Date date1;
    Date date2;
    ListeStock[] listeStock;
    double montant;

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
       if(date1!=null)
        {
            this.date1 = date1;
        }
        else{
            this.date1=new Date(System.currentTimeMillis());
        }
    }
    public void setDate1(String date1)
    {
        Date date=new Date(System.currentTimeMillis());
        if(!date1.equals(""))
        {
            date=Date.valueOf(date1);
        }
        this.setDate1(date);
    }
    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        if(date2!=null)
        {
            this.date2 = date2;
        }
        else{
            this.date2=new Date(System.currentTimeMillis());
        }
    }

     public void setDate2(String date1)
    {
        Date date=new Date(System.currentTimeMillis());
        if(!date1.equals(""))
        {
            date=Date.valueOf(date1);
        }
        this.setDate2(date);
    }
     
    public ListeStock[] getListeStock() {
        return listeStock;
    }

    public void setListeStock(ListeStock[] listeStock) {
        this.listeStock = listeStock;
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
     public EtatStock()
     {
         
     }
    public EtatStock(Date date1, Date date2, ListeStock[] listeStock) throws Exception{
        this.setDate1(date1);
        this.setDate2(date2);
        this.setListeStock(listeStock);
        this.setMontant(EtatStock.getMontant(listeStock));
    }
    
    public static EtatStock getEtatStock(Connection con,String article,String date1,String date2,String magasin) throws Exception
    {
        EtatStock e=new EtatStock();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
       Mouvement[] mouvement1=Article.getMouvement(con,date1,article,magasin);
       Mouvement[] mouvement2=Article.getMouvement(con,date2,article,magasin);
       ListeStock[] liste=ListeStock.getListeStock(con,mouvement1,mouvement2);
       Date daty1=new Date(System.currentTimeMillis());
       if(!date1.equals(""))
       {
           daty1=Date.valueOf(date1);
       }
       Date daty2=new Date(System.currentTimeMillis());
       if(!date2.equals(""))
       {
           daty2=Date.valueOf(date2);
       }
       e=new EtatStock(daty1,daty2,liste);
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
        return e;
    }
    
    public static double getMontant(ListeStock[] listeStock)
    {
        double montant=0;
        for(int i=0;i<listeStock.length;i++)
        {
            montant+=listeStock[i].getMontant();
        }
        return montant;
    }
}
