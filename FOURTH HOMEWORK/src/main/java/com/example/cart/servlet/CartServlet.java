package com.example.cart.servlet;

import com.example.cart.model.CartItem;
import com.example.cart.service.Cart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(false);
        Cart cart = session == null ? null : (Cart) session.getAttribute("CART");
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Корзина</title></head><body>");
        out.println("<h1>Корзина</h1>");
        out.println("<p><a href='catalog'>Вернуться в каталог</a></p>");
        if (cart == null || cart.isEmpty()) {
            out.println("<p>Корзина пуста</p>");
        } else {
            out.println("<table border='1' cellpadding='5'><tr><th>Товар</th><th>Кол-во</th><th>Цена</th><th>Сумма</th></tr>");
            for (CartItem item : cart.getItems()) {
                out.println("<tr><td>" + escape(item.getProduct().getName()) + "</td><td>" + item.getQuantity() + "</td><td>" + item.getProduct().getPrice() + "</td><td>" + item.getTotal() + "</td></tr>");
            }
            out.println("<tr><td colspan='3'><strong>Итого</strong></td><td><strong>" + cart.getTotal() + "</strong></td></tr>");
            out.println("</table>");
            out.println("<form method='post' action='checkout'><input type='submit' value='Оформить заказ'></form>");
        }
        out.println("</body></html>");
    }

    private String escape(String s) { return s == null ? "" : s.replace("<", "&lt;").replace(">", "&gt;"); }
}

