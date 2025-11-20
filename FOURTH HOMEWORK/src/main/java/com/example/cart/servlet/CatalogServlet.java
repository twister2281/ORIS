package com.example.cart.servlet;

import com.example.cart.model.Product;
import com.example.cart.service.ProductCatalog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CatalogServlet", urlPatterns = {"/catalog"})
public class CatalogServlet extends HttpServlet {
    private final ProductCatalog catalog = new ProductCatalog();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Каталог</title></head><body>");
        out.println("<h1>Каталог товаров</h1>");
        out.println("<p><a href='cart'>Перейти в корзину</a></p>");
        out.println("<ul>");
        for (Product p : catalog.listAll()) {
            out.println("<li>" + escape(p.getName()) + " - " + p.getPrice() + " руб. " +
                    "<a href='add-to-cart?id=" + p.getId() + "'>Добавить</a></li>");
        }
        out.println("</ul>");
        out.println("</body></html>");
    }

    private String escape(String s) { return s == null ? "" : s.replace("<", "&lt;").replace(">", "&gt;"); }
}

