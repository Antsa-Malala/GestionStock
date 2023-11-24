/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.Connexion;
import element.Article;
import element.Entree;
import element.Unite;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Antsa
 */
public class InsertionEntreeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         try {
            Connection c=Connexion.getConnect("postgres");
            try{
                String dateEntree=request.getParameter("dateEntree");
                String idarticle=request.getParameter("idarticle");
                String quantite=request.getParameter("quantite");
                String idmagasin=request.getParameter("idmagasin");
                String unite=request.getParameter("unite");
                Unite u=new Unite(unite,idarticle).getUnite(c);
                String pu=request.getParameter("pu");
                Article a=Article.getById(idarticle,c);
                if(a.uniteok(c, unite)){
                    Entree m=new Entree(c,dateEntree,idarticle,quantite,String.valueOf(u.getIdUnite()),pu,idmagasin);
                    m.insertEntree(c);
                    response.sendRedirect("EntreeController");   
                }
                else{
                    throw new Exception("Cette unite ne correspond pas a cette article");
                }
            }
            catch(Exception e)
            {  
                throw e;
            }
            finally{
                c.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            request.setAttribute("error",e);
            RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
