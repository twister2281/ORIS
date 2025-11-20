package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ProductViewServlet", urlPatterns = {"/product"})
public class ProductViewServlet extends HttpServlet {
    private final ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String idParam = req.getParameter("id");
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Товар</title></head><body>");
        out.println("<p><a href='products'>Назад к списку</a></p>");
        if (idParam == null) { out.println("<p>ID не указан</p></body></html>"); return; }
        try {
            long id = Long.parseLong(idParam);
            Product p = dao.findById(id).orElse(null);
            if (p == null) {
                out.println("<p>Товар не найден</p>");
            } else {
                out.println("<h1>" + esc(p.getName()) + "</h1>");
                out.println("<p>Цена: " + p.getPrice() + " руб.</p>");
                out.println("<p>Описание: " + esc(p.getDescription()) + "</p>");
                out.println("<p><a href='cart-add?id=" + p.getId() + "'>В корзину</a></p>");
            }
        } catch (NumberFormatException e) {
            out.println("<p>Некорректный ID</p>");
        }
        out.println("</body></html>");
    }

    private String esc(String s){ return s==null?"":s.replace("<","&lt;").replace(">","&gt;"); }
}

