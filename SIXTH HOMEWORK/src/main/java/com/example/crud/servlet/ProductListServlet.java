import java.util.List;

@WebServlet(name = "ProductListServlet", urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {
    private final ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        List<Product> list = dao.findAll();
        HttpSession session = req.getSession(false);
        Long userId = session == null ? null : (Long) session.getAttribute("USER_ID");
        String username = session == null ? null : (String) session.getAttribute("USERNAME");
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Товары</title></head><body>");
        out.println("<div style='float:right;'>" + (userId==null ? "<a href='login'>Войти</a> | <a href='register'>Регистрация</a>" : ("Привет, "+esc(username)+" | <a href='logout'>Выйти</a>")) + "</div>");
        out.println("<h1>Список товаров</h1>");
        if(userId!=null) out.println("<p><a href='product-new'>Добавить товар</a> | <a href='cart'>Корзина</a></p>");
        else out.println("<p><a href='cart'>Корзина</a> (нужен вход для добавления)</p>");
        if (list.isEmpty()) {
            out.println("<p>Нет товаров</p>");
        } else {
            out.println("<ul>");
            for (Product p : list) {
                out.println("<li><a href='product?id=" + p.getId() + "'>" + esc(p.getName()) + "</a> - " + p.getPrice() + " руб. " +
                        (userId!=null?"<a href='cart-add?id=" + p.getId() + "'>В корзину</a> | <a href='product-edit?id=" + p.getId() + "'>Редактировать</a> | <a href='product-delete?id=" + p.getId() + "' onclick='return confirm(\"Удалить?\")'>Удалить</a>":"(войдите для действий)") +
                        "</li>");
            }
            out.println("</ul>");
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("USER_ID")==null){ resp.sendRedirect("login?error="+url("Нужно войти")); return; }
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        if (isBlank(name) || isBlank(price)) { resp.sendRedirect("products"); return; }
        dao.create(new Product(name.trim(), new BigDecimal(price.trim()), description == null ? null : description.trim()));
        resp.sendRedirect("products");
    }

    private boolean isBlank(String s){ return s==null||s.trim().isEmpty(); }
    private String esc(String s){ return s==null?"":s.replace("<","&lt;").replace(">","&gt;"); }
    private String url(String s){ return s.replace(" ", "%20"); }
}
package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

