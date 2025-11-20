package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name="ProductListServlet", urlPatterns={"/products"})
public class ProductListServlet extends HttpServlet {
    private final ProductDao dao=new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page=parse(req.getParameter("page"),1); int size=parse(req.getParameter("size"),10);
        long total=dao.count(); long totalPages=(total+size-1)/size; if(page>totalPages && totalPages>0) page=(int)totalPages;
        List<Product> products=dao.findPaged(page,size);
        HttpSession session=req.getSession(false); Long userId=session==null?null:(Long)session.getAttribute("USER_ID");
        req.setAttribute("products",products); req.setAttribute("totalPages",totalPages); req.setAttribute("currentPage",page); req.setAttribute("size",size); req.setAttribute("userId",userId); req.getRequestDispatcher("/WEB-INF/jsp/productList.jsp").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        req.setCharacterEncoding("UTF-8"); String name=req.getParameter("name"); String price=req.getParameter("price"); String description=req.getParameter("description");
        if(blank(name)||blank(price)){ resp.sendRedirect("products"); return; }
        dao.create(new Product(name.trim(), new BigDecimal(price.trim()), description==null?null:description.trim())); resp.sendRedirect("products");
    }
    private boolean blank(String s){ return s==null||s.trim().isEmpty(); }
    private int parse(String p,int def){ try{ return Integer.parseInt(p);}catch(Exception e){ return def; } }
}

