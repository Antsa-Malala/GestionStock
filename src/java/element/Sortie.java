package element;

import connection.Connexion;
import element.Article;
import element.Magasin;
import element.Unite;
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
public class Sortie extends Mouvement{
    String typeMouvement;
    Entree entree;

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    
    public Entree getEntree() {
        return entree;
    }

    public void setEntree(Entree entree) {
        this.entree = entree;
    }
   
    public Sortie(Connection con,String date, String idarticle, String quantite, String idmagasin,String idunite)  throws Exception {
        super(con,date,idarticle,quantite,idmagasin,idunite);
    }
    public Sortie(Connection con,String idsortie,String date, String idarticle, String quantite, String idmagasin,String idunite)  throws Exception {
        super(con,idsortie,date,idarticle,quantite,idmagasin,idunite);
    }
    
    public Sortie(Connection con,int idsortie,Date date,String idarticle,double quantite,int idunite,double pu,String idmagasin,int identree) throws Exception
    {
        super(con,idsortie,date,idarticle,quantite,idunite,pu,idmagasin);
        this.setEntree(new Entree(identree).getById(con));
    }
    
    public Sortie(Connection con,int idsortie,Date date,String idarticle,double quantite,int idunite,double pu,String idmagasin) throws Exception
    {
        super(con,idsortie,date,idarticle,quantite,idunite,pu,idmagasin);
    }
    
    public Sortie(Connection con,int idsortie,Date date,String idarticle,double quantite,int idunite,double pu,String idmagasin,int identree,String typeMouvement) throws Exception
    {
        super(con,idsortie,date,idarticle,quantite,idunite,pu,idmagasin);
        this.setEntree(new Entree(identree).getById(con));
        this.setTypeMouvement(typeMouvement);
    }
    public Sortie(String idsortie) throws Exception
    {
        super.setIdMouvement(idsortie);
    }
/*  */
    public Sortie[] decomposer(Connection con,String sort) throws Exception
    {
        int co=0;
        Entree[] entree=this.getArticle().getEntree(this.getDateValidation(), this.getMagasin(), con,sort);
        ArrayList<Sortie> moove=new ArrayList<Sortie>();
        Sortie[] mouve=new Sortie[0];
        double sortie=this.getQuantite();
        int i=0;
        try{
            if(con==null)
            {
                con = Connexion.getConnect("");
                co=1;
            }
            while(i<entree.length&&sortie>0)
            {
                Entree actu=entree[i];
                double quantite=0;
                if(actu.getQuantite()>=sortie)
                {
                    quantite=sortie;
                }
                else{
                   quantite=actu.getQuantite();
                }
                Sortie m=new Sortie(con,actu.getIdMouvement(),this.getDateValidation(),actu.getArticle().getIdArticle(),quantite,actu.getUnite().getIdUnite(),actu.getPu(),actu.getMagasin().getIdMagasin(),actu.getIdMouvement());
                moove.add(m);
                sortie-=quantite;
                i++;
            }
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if( co==1 ){
                con.close();
            }
        }
        return moove.toArray(mouve);
    }
/*  */    
    public void sortir(Connection con,String idsortie,String idutilisateur) throws Exception
    {
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            int idsort=Integer.valueOf(idsortie);
            con.setAutoCommit(false);
            if(this.checkValidation(con)){
                Mouvement stock=this.getArticle().getStock(this.getDateValidation(), this.getMagasin(), con);
                System.out.println(stock.getQuantite()+" "+this.getQuantite());
                if(stock.getQuantite()<this.getQuantite()){
                    throw new Exception("Stock insuffisant. Il reste "+ stock.getQuantite());
                }
                else{
                    if(this.getArticle().getTypeSortie().equalsIgnoreCase("Fifo"))
                    {
                        sortie(con,"asc",idsort,idutilisateur);
                    }
                    else if(this.getArticle().getTypeSortie().equalsIgnoreCase("Lifo")){
                        sortie(con,"desc",idsort,idutilisateur);
                    }
                }
                con.commit();
            }
            else{
                throw new Exception("Cette date est anterieure a la derniere validation qui etait le "+this.getArticle().getLastMouvement(con, this.getMagasin()).getDateValidation());
            }
        }
        catch(Exception ex){
            con.rollback();
            throw ex;
        }
        finally{
            if(co==1)
            {
                con.close();
            }
        }
    }
/*  */
    public void sortie(Connection con,String sort,int idsortie,String idutilisateur) throws Exception
    {
        int co=0;
        try{
            Sortie[] decomposition=this.decomposer(con,sort);
            if(con==null)
            {
                con = Connexion.getConnect("");
                co=1;
            }
            con.setAutoCommit(false);
            for(int i=0;i<decomposition.length;i++)
            {
                decomposition[i].insertSortie(con,idsortie);     
            }
            this.insertValidation(con,idsortie,idutilisateur);     
            con.commit();
        }
        catch(Exception ex){
            con.rollback();
            throw ex;
        }
        finally{
            if( co==1 ){
                con.close();
            }
        }
    }
/*  */    
    public void insertSortie(Connection con,int idsortie) throws Exception
    {
        PreparedStatement st = null;
        int co=0;
        try{
            if(con==null)
            {
                con = Connexion.getConnect("");
                co=1;
            }
            String requete = "insert into mouvement values(default,?,?,?,?,?,?,?,?);";
            st = con.prepareStatement(requete);
            st.setDate(1,this.getDate());
            st.setString(2, this.getEntree().getArticle().getIdArticle());
            st.setDouble(3, this.getQuantite());
            st.setInt(4, this.getEntree().getUnite().getIdUnite());
            st.setDouble(5, this.getEntree().getPu());
            st.setString(6, this.getEntree().getMagasin().getIdMagasin());
            st.setInt(7,this.getEntree().getIdMouvement());
            st.setInt(8,idsortie);
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
    
    
    
    public void insertValidation(Connection con,int idsortie,String idutilisateur) throws Exception
    {
        PreparedStatement st = null;
        int co=0;
        try{
            if(con==null)
            {
                con = Connexion.getConnect("");
                co=1;
            }
            String requete = "insert into valide values(?,?,?);";
            st = con.prepareStatement(requete);
            st.setDate(1,this.getDateValidation());
            st.setString(2, idutilisateur);
            st.setDouble(3, idsortie);
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
    
    public void insert(Connection con) throws Exception
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
            String requete = "insert into mouvementProvisoire values(default,?,?,?,?,?);";
            st = con.prepareStatement(requete);
            st.setDate(1,this.getDate());
            st.setString(2, this.getArticle().getIdArticle());
            st.setDouble(3, quantite);
            st.setInt(4, reference.getIdUnite());
            st.setString(5, this.getMagasin().getIdMagasin());
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
    
    public static Sortie[] getSortieInvalide(Connection con) throws Exception
    {
        Sortie[] sor=new Sortie[0];
        ArrayList<Sortie> sort=new ArrayList<Sortie>();
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from mouvementInvalide";
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                Sortie a=new Sortie(con,res.getInt("idmouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),0,res.getString("idmagasin"));
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
    
    public Sortie getById(Connection con) throws Exception{
        Sortie sortie=null;
        Statement st = null;
        ResultSet res = null;
        int co=0;
        try{
            if(con==null)
            {
                con=Connexion.getConnect("postgres");
                co=1;
            }
            String requete = "select * from mouvementInvalide where idmouvement="+this.getIdMouvement();
            st = con.createStatement();
            res = st.executeQuery(requete);
            while(res.next())
            {
                sortie=new Sortie(con,res.getInt("idmouvement"),res.getDate("datemouvement"),res.getString("idarticle"),res.getDouble("quantite"),res.getInt("idunite"),0,res.getString("idmagasin"));   
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
        return sortie;
    }
    
    public void modif(Connection con) throws Exception
    {
        PreparedStatement st = null;
        int co=0;
        try{
            if(con==null)
            {
                con = Connexion.getConnect("");
                co=1;
            }
            Unite u=new Unite(this.getUnite().getNomUnite(),this.getArticle().getIdArticle()).getUnite(con);
            Unite reference=u.getUniteReference(con);
            double quantite=this.getQuantite()*u.getReference();
            double pu=this.getPu()/u.getReference();
            String requete = "update mouvementProvisoire set datemouvement=?,idarticle=?,quantite=?,idunite=?,idmagasin=? where idmouvement=?";
            st = con.prepareStatement(requete);
            st.setDate(1,this.getDate());
            st.setString(2, this.getArticle().getIdArticle());
            st.setDouble(3, quantite);
            st.setInt(4,reference.getIdUnite());
            st.setString(5, this.getMagasin().getIdMagasin());
            st.setInt(6, this.getIdMouvement());
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
    
    public boolean checkValidation(Connection con) throws Exception
    {
        try{
            Mouvement last=this.getArticle().getLastMouvement(con, this.getMagasin());
            if(last==null)
            {
                return true;
            }
            else{
                if(this.getDateValidation().after(last.getDateValidation()))
                {
                    return true;
                }    
            }
            return false;
        }
        catch(Exception e)
        {
            throw e;
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
            String requete = "insert into mouvementInsere values(default,?,?,?,?,?,?,'Sortie');";
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
