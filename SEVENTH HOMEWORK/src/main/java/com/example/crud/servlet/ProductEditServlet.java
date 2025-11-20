package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name="ProductEditServlet", urlPatterns={"/product-edit"})
public class ProductEditServlet extends HttpServlet {
    private final ProductDao dao=new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        String idParam=req.getParameter("id"); Product p=null; if(idParam!=null){ try{ p=dao.findById(Long.parseLong(idParam)).orElse(null);}catch(NumberFormatException ignored){} }
        req.setAttribute("product",p); req.getRequestDispatcher("/WEB-INF/jsp/productForm.jsp").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        req.setCharacterEncoding("UTF-8"); String idParam=req.getParameter("id"); String name=req.getParameter("name"); String price=req.getParameter("price"); String description=req.getParameter("description");
        if(blank(idParam)||blank(name)||blank(price)){ req.setAttribute("error","Заполните все поля"); req.getRequestDispatcher("/WEB-INF/jsp/productForm.jsp").forward(req,resp); return; }
        try{ long id=Long.parseLong(idParam); dao.update(new Product(id,name.trim(), new BigDecimal(price.trim()), description==null?null:description.trim())); }catch(NumberFormatException ignored){}
        resp.sendRedirect("products");
    }
    private boolean blank(String s){ return s==null||s.trim().isEmpty(); }
}

