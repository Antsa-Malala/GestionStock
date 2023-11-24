/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package element;

import connection.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Antsa
 */
public class Entree extends Mouvement{
     
     public Entree(Connection c,int identree,Date dateEntree,String idarticle,double quantite,int idunite,double pu,String idmagasin) throws Exception
     {
         super(c,identree,dateEntree,idarticle,quantite,idunite,pu,idmagasin);
     }
     
     public Entree(Connection c,String dateEntree,String idarticle,String quantite,String idunite,String pu,String idmagasin) throws Exception
     {
         super(c,dateEntree,idarticle,quantite,pu,idmagasin,idunite,1);
     }
     
     public Entree(int identree) throws Exception
     {
         super.setIdMouvement(identree);
     }

     public Entree getById(Connection con)throws Exception{
        Entree entree=null;
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from entree where idmouvement="+this.getIdMouvement();
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                entree=new Entree(con,res.getInt("idmouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),res.getDouble("pu"),res.getString("idmagasin"));   
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
        return entree;
     }
    /*  */ 
     public double getEtatStockParEntree(Connection con) throws Exception
     {
        double reste=0;
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select coalesce(sum(quantite),0) as nivoaka  from sortie where idmouvemententree="+this.getIdMouvement()+" and idarticle='"+this.getArticle().getIdArticle()+"' and idmagasin='"+this.getMagasin().getIdMagasin()+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                reste=this.getQuantite()-res.getDouble("nivoaka");
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
         return reste;
     }
     
     public void insertEntree(Connection con) throws Exception
    {
        PreparedStatement st = null;
        int co=0;
        try{
            if(con==null)
            {
                con = Connexion.getConnect("");
                co=1;
            }
            con.setAutoCommit(false);
            Unite u=new Unite(this.getUnite().getNomUnite(),this.getArticle().getIdArticle()).getUnite(con);
            Unite reference=u.getUniteReference(con);
            double quantite=this.getQuantite()*u.getReference();
            double pu=this.getPu()/u.getReference();
            String requete = "insert into mouvement values(default,?,?,?,?,?,?,null,null)";
            st = con.prepareStatement(requete);
            st.setDate(1,this.getDate());
            st.setString(2, this.getArticle().getIdArticle());
            st.setDouble(3,quantite);
            st.setInt(4,reference.getIdUnite());
            st.setDouble(5, pu);
            st.setString(6, this.getMagasin().getIdMagasin());
            st.execute();
            this.insertion(con);
            con.commit();
        }
        catch(Exception ex){
            con.rollback();
            throw ex;
        }
        finally{
            if(st != null ){
                st.close();
            }
            if( co==1 ){
                con.close();
            }
        }
    }
     
    public void insertion(Connection con) throws Exception
    {
        PreparedStatement st = null;
        int co=0;
        try{
            if(con==null)
            {
                con = Connexion.getConnect("");
                co=1;
            }
            String requete = "insert into mouvementInsere values(default,?,?,?,?,?,?,'Entree');";
            st = con.prepareStatement(requete);
            st.setDate(1,this.getDate());
            st.setString(2, this.getArticle().getIdArticle());
            st.setDouble(3, this.getQuantite());
            st.setInt(4, this.getUnite().getIdUnite());
            st.setDouble(5, this.getPu());
            st.setString(6, this.getMagasin().getIdMagasin());
            st.execute();
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(st != null ){
                st.close();
            }
            if( co==1 ){
                con.close();
            }
        }
    }
}
