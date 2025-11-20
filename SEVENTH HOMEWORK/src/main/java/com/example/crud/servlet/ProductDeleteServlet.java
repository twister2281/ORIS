package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="ProductDeleteServlet", urlPatterns={"/product-delete"})
public class ProductDeleteServlet extends HttpServlet {
    private final ProductDao dao=new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        String idParam=req.getParameter("id"); if(idParam!=null){ try{ dao.delete(Long.parseLong(idParam)); }catch(NumberFormatException ignored){} }
        resp.sendRedirect("products");
    }
}

