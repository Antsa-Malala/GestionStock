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
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Antsa
 */
public class Magasin {
    String idMagasin;
    String nomMagasin;
    Time ouverture;
    Time fermeture;
    

    public String getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(String idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getNomMagasin() {
        return nomMagasin;
    }

    public void setNomMagasin(String nomMagasin) {
        this.nomMagasin = nomMagasin;
    }

    public Time getOuverture() {
        return ouverture;
    }

    public void setOuverture(Time ouverture) {
        this.ouverture = ouverture;
    }
    
     public void setOuverture(String ouverture) {
        Time ouvre=Time.valueOf(ouverture);
        this.ouverture = ouvre;
    }

    public Time getFermeture() {
        return fermeture;
    }

    public void setFermeture(Time fermeture) {
        this.fermeture = fermeture;
    }
    
    public void setFermeture(String fermeture) {
        Time ferme=Time.valueOf(fermeture);
        this.ouverture = ferme;
    }
    
    public Magasin()
    {
        
    }

    public Magasin(String idMagasin, String nomMagasin, Time ouverture, Time fermeture) {
        this.setIdMagasin(idMagasin);
        this.setNomMagasin(nomMagasin);
        this.setOuverture(ouverture);
        this.setFermeture(fermeture);
    }
/*  */
    public static Magasin[] getAllMagasin(Connection con) throws Exception
    {
        Magasin[] m=new Magasin[0];
        ArrayList<Magasin> mag=new ArrayList<Magasin>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from magasin";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Magasin a=new Magasin(res.getString("idMagasin"),res.getString("nomMagasin"),res.getTime("ouverture"),res.getTime("fermeture"));
                mag.add(a);
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
        return mag.toArray(m);
    }
/*  */    
    public static Magasin getById(String idMagasin,Connection con) throws Exception
    {
        Magasin m=null;
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from magasin where idmagasin='"+idMagasin+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                 m=new Magasin(res.getString("idMagasin"),res.getString("nomMagasin"),res.getTime("ouverture"),res.getTime("fermeture"));
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
        return m;
    }
/*  */    
    public boolean checkOuverture(Time achecker)
    {
        boolean ouverture=false;
        if (achecker.after(this.getOuverture()) && achecker.before(this.getFermeture())) {
            ouverture=true;
        }
        return ouverture;
    }
/*  */    
    public Mouvement getStock(Date daty,Article article,Connection con)throws Exception
    {
        if(daty==null)
        {
            daty=new Date(System.currentTimeMillis());
        }
        Mouvement m=new Mouvement(daty,article,0,new Unite(),0,this);
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
        Entree[] entree=this.getAllEntree(daty, article, con,"asc");
        Sortie[] sortie=this.getAllSortie(daty, article, con);
        Mouvement in=this.getMoyenne(entree,article,daty);
        Mouvement out=this.getMoyenne(sortie,article,daty);
        int taille=entree.length;
            if(taille>0)
            {
                Mouvement farany=entree[taille-1];
                m=new Mouvement(daty,article,in.getQuantite()-out.getQuantite(),farany.getUnite(),in.getPu(),this);
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
        return m;
    }
/*  */     
    public Mouvement getMoyenne(Mouvement[] entrees,Article article,Date daty)throws Exception{
        if(daty==null)
        {
            daty=new Date(System.currentTimeMillis());
        }
        Mouvement e=new Mouvement(daty,article,0,new Unite(),0,this);
        double quantite=0;
        double pu=0;
        int taille=entrees.length;
        for(int i=0;i<taille;i++)
        {
            quantite+=entrees[i].getQuantite();
            pu+=entrees[i].getPu()*entrees[i].getQuantite();
        }
        if(pu>0)
        {
            pu/=quantite;
        }
        
        Mouvement farany=new Mouvement();
        if(taille>=1)
        {
            farany=entrees[taille-1];
            e=new Mouvement(farany.getDate(),farany.getArticle(),quantite,farany.getUnite(),pu,farany.getMagasin()); 
        }
        return e;
    }
/*  */     
    
    public Entree[] getAllEntree(Date daty,Article article,Connection con,String entre)throws Exception{
        Entree[] entree=new Entree[0];
        ArrayList<Entree> art=new ArrayList<Entree>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from entree where datemouvement<='"+daty+"' and idarticle='"+article.getIdArticle()+"' and idmagasin='"+this.getIdMagasin()+"'order by datemouvement "+entre+",idmouvement "+entre;
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Entree e=new Entree(con,res.getInt("idmouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),res.getDouble("pu"),res.getString("idmagasin"));
                art.add(e);
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
        return art.toArray(entree);
    }
    
    public Entree[] getEntree(Date daty,Article article,Connection con,String entre)throws Exception{
        if(daty==null)
        {
            daty=new Date(System.currentTimeMillis());
        }
        Entree[] entree=new Entree[0];
        ArrayList<Entree> art=new ArrayList<Entree>();
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            Entree[] allentree=this.getAllEntree(daty, article, con, entre);
            for(int i=0;i<allentree.length;i++)
            {
                double reste=allentree[i].getEtatStockParEntree(con);
                if(reste>0)
                {
                    allentree[i].setQuantite(reste);
                    art.add(allentree[i]);
                }
            }
        }
      catch(Exception ex){
          throw ex;
      }
      finally{
          if(co==1)
          {
              con.close();
          }
      }
      return art.toArray(entree);
   }
    
 public Sortie[] getAllSortie(Date daty,Article article,Connection con)throws Exception{
        Sortie[] sortie=new Sortie[0];
        ArrayList<Sortie> art=new ArrayList<Sortie>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from sortieType where datemouvement<='"+daty+"' and idarticle='"+article.getIdArticle()+"' and idmagasin='"+this.getIdMagasin()+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Sortie e=new Sortie(con,res.getInt("idmouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),res.getDouble("pu"),res.getString("idmagasin"),res.getInt("idmouvemententree"),res.getString("typesortie"));
                art.add(e);
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
        return art.toArray(sortie);
     }    
 
     public Mouvement getLastMouvement(Connection con,Article article) throws Exception
     {
        Mouvement m=null;
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from mouvementvalide where datevalidation=(select max(datevalidation) from mouvementvalide where idarticle='"+article.getIdArticle()+"' and idmagasin='"+this.getIdMagasin()+"') and idarticle='"+article.getIdArticle()+"' and idmagasin='"+this.getIdMagasin()+"'";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                 m=new Mouvement(con,res.getInt("idmouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),0,res.getString("idmagasin"),res.getDate("datevalidation"));
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
        return m;
     }
}
