package com.example.cart.servlet;

import com.example.cart.model.Product;
import com.example.cart.service.Cart;
import com.example.cart.service.ProductCatalog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddToCartServlet", urlPatterns = {"/add-to-cart"})
public class AddToCartServlet extends HttpServlet {
    private final ProductCatalog catalog = new ProductCatalog();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect("catalog");
            return;
        }
        Product product = catalog.findById(id).orElse(null);
        if (product == null) {
            resp.sendRedirect("catalog");
            return;
        }
        HttpSession session = req.getSession(true);
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("CART", cart);
        }
        cart.add(product);
        resp.sendRedirect("catalog");
    }
}

