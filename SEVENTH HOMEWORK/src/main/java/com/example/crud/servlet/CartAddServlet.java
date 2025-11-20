package com.example.crud.servlet;

import com.example.crud.service.CartIds;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="CartAddServlet", urlPatterns={"/cart-add"})
public class CartAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        String idParam=req.getParameter("id"); if(idParam!=null){ try{ long id=Long.parseLong(idParam); CartIds cart=(CartIds)session.getAttribute("CART_IDS"); if(cart==null){ cart=new CartIds(); session.setAttribute("CART_IDS",cart);} cart.add(id);}catch(NumberFormatException ignored){} }
        resp.sendRedirect("products");
    }
}

