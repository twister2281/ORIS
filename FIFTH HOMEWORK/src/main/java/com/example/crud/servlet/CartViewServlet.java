package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;
import com.example.crud.service.CartIds;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet(name = "CartViewServlet", urlPatterns = {"/cart"})
public class CartViewServlet extends HttpServlet {
    private final ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(false);
        CartIds cart = session == null ? null : (CartIds) session.getAttribute("CART_IDS");
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Корзина</title></head><body>");
        out.println("<h1>Корзина</h1>");
        out.println("<p><a href='products'>К товарам</a></p>");
        if (cart == null || cart.isEmpty()) {
            out.println("<p>Корзина пуста</p>");
        } else {
            BigDecimal total = BigDecimal.ZERO;
            out.println("<table border='1' cellpadding='5'><tr><th>ID</th><th>Название</th><th>Кол-во</th><th>Цена</th><th>Сумма</th></tr>");
            for (var entry : cart.getAll().entrySet()) {
                long id = entry.getKey();
                int qty = entry.getValue();
                Product p = dao.findById(id).orElse(null);
                if (p == null) continue; // пропускаем удалённые
                BigDecimal line = p.getPrice().multiply(BigDecimal.valueOf(qty));
                total = total.add(line);
                out.println("<tr><td>"+id+"</td><td>"+esc(p.getName())+"</td><td>"+qty+"</td><td>"+p.getPrice()+"</td><td>"+line+"</td></tr>");
            }
            out.println("<tr><td colspan='4'><strong>Итого</strong></td><td><strong>"+total+"</strong></td></tr>");
            out.println("</table>");
            out.println("<form method='post' action='checkout'><input type='submit' value='Оформить заказ'></form>");
        }
        out.println("</body></html>");
    }

    private String esc(String s){ return s==null?"":s.replace("<","&lt;").replace(">","&gt;"); }
}

