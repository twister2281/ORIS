package com.example.crud.servlet;

import com.example.crud.service.CartIds;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CartAddServlet", urlPatterns = {"/cart-add"})
public class CartAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                HttpSession session = req.getSession(true);
                CartIds cart = (CartIds) session.getAttribute("CART_IDS");
                if (cart == null) { cart = new CartIds(); session.setAttribute("CART_IDS", cart); }
                cart.add(id);
            } catch (NumberFormatException ignored) {}
        }
        resp.sendRedirect("products");
    }
}

