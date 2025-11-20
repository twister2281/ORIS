package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name="ProductNewServlet", urlPatterns={"/product-new"})
public class ProductNewServlet extends HttpServlet {
    private final ProductDao dao=new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        req.getRequestDispatcher("/WEB-INF/jsp/productForm.jsp").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        req.setCharacterEncoding("UTF-8"); String name=req.getParameter("name"); String price=req.getParameter("price"); String description=req.getParameter("description");
        if(blank(name)||blank(price)){ req.setAttribute("error","Название и цена обязательны"); req.getRequestDispatcher("/WEB-INF/jsp/productForm.jsp").forward(req,resp); return; }
        dao.create(new Product(name.trim(), new BigDecimal(price.trim()), description==null?null:description.trim())); resp.sendRedirect("products");
    }
    private boolean blank(String s){ return s==null||s.trim().isEmpty(); }
}

