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

@WebServlet(name = "ProductNewServlet", urlPatterns = {"/product-new"})
public class ProductNewServlet extends HttpServlet {
    private final ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Новый товар</title></head><body>");
        out.println("<h1>Добавить товар</h1>");
        out.println("<form method='post' action='product-new'>");
        out.println("Название: <input name='name'><br>");
        out.println("Цена: <input name='price'><br>");
        out.println("Описание:<br><textarea name='description'></textarea><br>");
        out.println("<input type='submit' value='Создать'>");
        out.println("</form>");
        out.println("<p><a href='products'>Назад</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        if (isBlank(name) || isBlank(price)) {
            resp.sendRedirect("product-new");
            return;
        }
        dao.create(new Product(name.trim(), new BigDecimal(price.trim()), description == null ? null : description.trim()));
        resp.sendRedirect("products");
    }

    private boolean isBlank(String s){ return s==null||s.trim().isEmpty(); }
}

