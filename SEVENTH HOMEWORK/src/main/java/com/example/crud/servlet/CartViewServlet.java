package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.CartLine;
import com.example.crud.model.Product;
import com.example.crud.service.CartIds;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="CartViewServlet", urlPatterns={"/cart"})
public class CartViewServlet extends HttpServlet {
    private final ProductDao dao=new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); CartIds cart=session==null?null:(CartIds)session.getAttribute("CART_IDS");
        List<CartLine> lines=new ArrayList<>(); BigDecimal total=BigDecimal.ZERO;
        if(cart!=null){ for(var e:cart.getAll().entrySet()){ Product p=dao.findById(e.getKey()).orElse(null); if(p==null) continue; CartLine line=new CartLine(p,e.getValue()); lines.add(line); total=total.add(line.getLineTotal()); } }
        req.setAttribute("lines",lines); req.setAttribute("total",total); req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req,resp);
    }
}

