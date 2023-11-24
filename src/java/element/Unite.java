/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package element;

import connection.Connexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Antsa
 */
public class Unite {
    int idUnite;
    String nomUnite;
    double reference;
    String idArticle;

    public int getIdUnite() {
        return idUnite;
    }

    public void setIdUnite(int idunite) throws Exception{
        if(idunite<=0)
        {
            throw new Exception("id unite invalide");
        }
        else{
        this.idUnite = idunite;
            
        }
    }
    
    public void setIdUnite(String idunite) throws Exception{
        int unit=Integer.valueOf(idunite);
        this.setIdUnite(unit);
    }

    public String getNomUnite() {
        return nomUnite;
    }

    public void setNomUnite(String nomUnite) {
        this.nomUnite = nomUnite;
    }

    public double getReference() {
        return reference;
    }

    public void setReference(double reference)throws Exception {
        this.reference=1;
        if(reference<=0)
        {
            throw new Exception("Reference invalide");
        }else{
            this.reference = reference;
        }
    }
    
    public void setReference(String reference)throws Exception
    {
        double ref=Double.valueOf(reference);
        this.setReference(ref);
    }

    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idarticle) {
        this.idArticle = idarticle;
    }
    public Unite(int idunite, String nomUnite, double reference, String idArticle) throws Exception{
        this.setIdUnite(idunite);
        this.setNomUnite(nomUnite);
        this.setReference(reference);
        this.setIdArticle(idArticle);
    }
    
    public Unite(String nomUnite, String idArticle) throws Exception{
        this.setNomUnite(nomUnite);
        this.setIdArticle(idArticle);
    }
    public Unite(String nomUnite) throws Exception{
        this.setNomUnite(nomUnite);
    }
    public Unite() throws Exception{

    }
    
/*  */
    public static Unite[] getAllUnite(Connection con) throws Exception
    {
        Unite[] unite=new Unite[0];
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
            String requete = "select distinct(nomUnite) from unite";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Unite a=new Unite(res.getString("nomUnite"));
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
        return art.toArray(unite);
    }
/*  */    
    public static Unite getById(String idUnite,Connection con) throws Exception
    {
        int id=Integer.valueOf(idUnite);
        Unite u=new Unite();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from unite where idunite="+id;
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                u=new Unite(res.getInt("idunite"),res.getString("nomUnite"),res.getDouble("reference"),res.getString("categorie"));
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
        return u;
    }
    
    public Unite getUniteReference(Connection con) throws Exception
    {
        Unite u=new Unite();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from unite where categorie='"+this.getIdArticle()+"' and reference=1";
            System.out.println(requete);
            st = con.createStatement();
            res = st.executeQuery(requete);
            System.out.println("haha");
            while(res.next())
            {
                u=new Unite(res.getInt("idunite"),res.getString("nomUnite"),res.getDouble("reference"),res.getString("categorie"));
                System.out.println("huhu"+u.getIdUnite());
            }
            System.out.println("hahu");
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
        return u;
    }
    
    public Unite getUnite(Connection con) throws Exception
    {
        Unite u=new Unite();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from unite where categorie='"+this.getIdArticle()+"' and nomUnite='"+this.getNomUnite()+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                u=new Unite(res.getInt("idunite"),res.getString("nomUnite"),res.getDouble("reference"),res.getString("categorie"));
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
        return u;
    }
}
