package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "ProductListServlet", urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {
    private final ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        List<Product> list = dao.findAll();
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Товары</title></head><body>");
        out.println("<h1>Список товаров</h1>");
        out.println("<p><a href='product-new'>Добавить товар</a> | <a href='cart'>Корзина</a></p>");
        if (list.isEmpty()) {
            out.println("<p>Нет товаров</p>");
        } else {
            out.println("<ul>");
            for (Product p : list) {
                out.println("<li><a href='product?id=" + p.getId() + "'>" + esc(p.getName()) + "</a> - " + p.getPrice() + " руб. " +
                        "<a href='cart-add?id=" + p.getId() + "'>В корзину</a> | " +
                        "<a href='product-edit?id=" + p.getId() + "'>Редактировать</a> | " +
                        "<a href='product-delete?id=" + p.getId() + "' onclick='return confirm(\"Удалить?\")'>Удалить</a></li>");
            }
            out.println("</ul>");
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // create via POST to /products (можно альтернативно формы)
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        if (isBlank(name) || isBlank(price)) {
            resp.sendRedirect("products");
            return;
        }
        dao.create(new Product(name.trim(), new BigDecimal(price.trim()), description == null ? null : description.trim()));
        resp.sendRedirect("products");
    }

    private boolean isBlank(String s){ return s==null||s.trim().isEmpty(); }
    private String esc(String s){ return s==null?"":s.replace("<","&lt;").replace(">","&gt;"); }
}

