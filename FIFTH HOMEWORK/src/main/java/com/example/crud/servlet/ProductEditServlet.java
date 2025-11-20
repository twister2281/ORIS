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

@WebServlet(name = "ProductEditServlet", urlPatterns = {"/product-edit"})
public class ProductEditServlet extends HttpServlet {
    private final ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String idParam = req.getParameter("id");
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Редактирование</title></head><body>");
        out.println("<p><a href='products'>Назад</a></p>");
        if (idParam == null) { out.println("ID не указан</body></html>"); return; }
        try {
            long id = Long.parseLong(idParam);
            Product p = dao.findById(id).orElse(null);
            if (p == null) { out.println("Товар не найден</body></html>"); return; }
            out.println("<h1>Редактировать товар #"+id+"</h1>");
            out.println("<form method='post' action='product-edit'>");
            out.println("<input type='hidden' name='id' value='"+id+"'>");
            out.println("Название: <input name='name' value='"+esc(p.getName())+"'><br>");
            out.println("Цена: <input name='price' value='"+p.getPrice()+"'><br>");
            out.println("Описание:<br><textarea name='description'>"+esc(p.getDescription())+"</textarea><br>");
            out.println("<input type='submit' value='Сохранить'>");
            out.println("</form>");
        } catch (NumberFormatException e) {
            out.println("Некорректный ID");
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        if (isBlank(idParam) || isBlank(name) || isBlank(price)) { resp.sendRedirect("products"); return; }
        try {
            long id = Long.parseLong(idParam);
            Product p = new Product(id, name.trim(), new BigDecimal(price.trim()), description == null ? null : description.trim());
            dao.update(p);
        } catch (NumberFormatException ignored) {}
        resp.sendRedirect("products");
    }

    private boolean isBlank(String s){ return s==null||s.trim().isEmpty(); }
    private String esc(String s){ return s==null?"":s.replace("<","&lt;").replace(">","&gt;"); }
}

