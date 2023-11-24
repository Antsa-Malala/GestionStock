/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connection.Connexion;
import element.Mouvement;
import element.Magasin;
import element.Sortie;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Antsa
 */
public class StockController extends HttpServlet {

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
                String dateSortie=request.getParameter("dateSortie");
                String idarticle=request.getParameter("idarticle");
                String quantite=request.getParameter("quantite");
                String idmagasin=request.getParameter("idmagasin");
                String idunite=request.getParameter("idunite");
                Sortie m=new Sortie(c,dateSortie,idarticle,quantite,idmagasin,idunite);
                Mouvement stock=Magasin.getById(idmagasin, c).getStock(m.getDate(), m.getArticle(), c);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                if(stock.getQuantite()<Double.valueOf(quantite))
                {
                    try (PrintWriter writer = response.getWriter()) {
                        writer.print("Stock insuffisant. Il ne reste que "+ stock.getQuantite());
                        writer.flush();
                    }
                }
            }
            catch(Exception e)
            {
                try (PrintWriter writer = response.getWriter()) {
                    writer.print(e.getMessage());
                    writer.flush();
                }
            }
            finally{
                c.close();
            }
        }
        catch(Exception e)
        {
           e.printStackTrace();
           try (PrintWriter writer = response.getWriter()) {
                writer.print(e.getMessage());
                writer.flush();
            }
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
