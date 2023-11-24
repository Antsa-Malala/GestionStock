/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package element;

import java.sql.ResultSet;
import java.sql.Statement;
import connection.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Antsa
 */
public class Article {
    String idArticle;
    String nomArticle;
    String typeSortie;

    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getTypeSortie() {
        return typeSortie;
    }

    public void setTypeSortie(String typeSortie) {
        this.typeSortie = typeSortie;
    }
    

    public Article(String idArticle, String nomArticle,String typeSortie) {
        this.setIdArticle(idArticle);
        this.setNomArticle(nomArticle);
        this.setTypeSortie(typeSortie);
    }
    public Article(){
        
    }
/*  */    
    public static Article[] getAllArticle(Connection con) throws Exception
    {
        Article[] article=new Article[0];
        ArrayList<Article> art=new ArrayList<Article>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from article";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Article a=new Article(res.getString("idarticle"),res.getString("nomarticle"),res.getString("typesortie"));
                art.add(a);
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
        return art.toArray(article);
        
    }
/*  */    
    public static Article getById(String idArticle,Connection con) throws Exception
    {
        Article a=new Article();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from article where idarticle='"+idArticle+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                a=new Article(res.getString("idarticle"),res.getString("nomarticle"),res.getString("typesortie"));
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
        return a;
    }
/*  */      
    public Mouvement getStock(Date daty,Magasin magasin,Connection con)throws Exception
    {
        if(daty==null)
        {
            daty=new Date(System.currentTimeMillis());
        }
        return magasin.getStock(daty, this, con);
    }
/*  */      
    public Mouvement getMoyenne(Mouvement[] entrees,Magasin magasin,Date daty)throws Exception{
        if(daty==null)
        {
            daty=new Date(System.currentTimeMillis());
        }
        return magasin.getMoyenne(entrees, this, daty);
    }
    
    public Entree[] getEntree(Date daty,Magasin magasin,Connection con,String entre)throws Exception{
        return magasin.getEntree(daty, this, con, entre);
    }
/*  */ 
    public static Article[] getArticle(Connection con,String reference) throws Exception
    {
        Article[] article=new Article[0];
        ArrayList<Article> art=new ArrayList<Article>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from article where idArticle like '"+reference+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Article a=new Article(res.getString("idarticle"),res.getString("nomarticle"),res.getString("typesortie"));
                art.add(a);
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
        return art.toArray(article);
    }
/*  */     
    public Mouvement[] getStock(Connection con,String date,String magasin) throws Exception
    {
        Date daty=new Date(System.currentTimeMillis());
        if(!date.equals(""))
        {
            daty=Date.valueOf(date);
        }
        Mouvement[] mouvement=new Mouvement[0];
        ArrayList<Mouvement> move=new ArrayList<Mouvement>();
        if(magasin.equals("%"))
        {
            Magasin[] all=Magasin.getAllMagasin(con);
            for(int i=0;i<all.length;i++)
            {
                Mouvement m=all[i].getStock(daty, this,con);
                move.add(m);
            }
        }else{
            Mouvement m=Magasin.getById(magasin, con).getStock(daty, this, con);
            move.add(m);
        }
        return move.toArray(mouvement);
    }
 /*  */        
    public static Mouvement[] getMouvement(Connection con,String date,String article,String magasin) throws Exception
    {
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
            Article[] all=Article.getArticle(con, article);
            for(int i=0;i<all.length;i++)
            {
              Mouvement[] mo=all[i].getStock(con, date, magasin);
              move.addAll(Arrays.asList(mo));
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
    
/*  */    
    public double getMontant(Connection con,Date date1,Date date2,Magasin magasin) throws Exception
    {
        double montant=0;
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            Entree[] entree=magasin.getAllEntree(date2, this, con,"asc");
            Sortie[] sortie=magasin.getAllSortie(date2, this,con);
            double in=Mouvement.getMontant(entree);
            double out=Mouvement.getMontant(sortie);
            montant= in-out;
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
        return montant;
    }
    
    public Entree[] getAllEntree(Date daty,Magasin magasin,Connection con,String entre)throws Exception{
        return magasin.getAllEntree(daty, this, con, entre);
    }
    
    public Sortie[] getAllSortie(Date daty,Magasin magasin,Connection con)throws Exception{
        return magasin.getAllSortie(daty, this, con);
    }
    
    public Mouvement getLastMouvement(Connection con,Magasin magasin) throws Exception
    {
        return magasin.getLastMouvement(con,this);
    }
    
    public Unite[] getUnite(Connection con) throws Exception
    {
        Unite[] unit=new Unite[0];
        ArrayList<Unite> art=new ArrayList<Unite>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from unite where categorie='"+this.getIdArticle()+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Unite u=new Unite(res.getInt("idunite"),res.getString("nomUnite"),res.getDouble("reference"),res.getString("categorie"));
                art.add(u);
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
        return art.toArray(unit);
    }

    public boolean uniteok(Connection con, String unite) throws Exception {
        boolean ok = false;
        Unite[] unit = this.getUnite(con);
        for (int i = 0; i < unit.length; i++) {
            String u = unit[i].getNomUnite();

            if (unite.equals(u)) {
                ok = true;
                break; 
            }

            System.out.println("jean");
        }

        return ok;
    }

}
    