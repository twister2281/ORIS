package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="ProductViewServlet", urlPatterns={"/product"})
public class ProductViewServlet extends HttpServlet {
    private final ProductDao dao=new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam=req.getParameter("id"); Product p=null; if(idParam!=null){ try{ p=dao.findById(Long.parseLong(idParam)).orElse(null);}catch(NumberFormatException ignored){} }
        req.setAttribute("product",p); req.getRequestDispatcher("/WEB-INF/jsp/productView.jsp").forward(req,resp);
    }
}

