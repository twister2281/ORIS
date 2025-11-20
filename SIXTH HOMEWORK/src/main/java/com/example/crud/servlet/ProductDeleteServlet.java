package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ProductDeleteServlet", urlPatterns = {"/product-delete"})
public class ProductDeleteServlet extends HttpServlet {
    private final ProductDao dao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("USER_ID")==null){ resp.sendRedirect("login?error="+url("Нужно войти")); return; }
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try { long id = Long.parseLong(idParam); dao.delete(id); } catch (NumberFormatException ignored) {}
        }
        resp.sendRedirect("products");
    }
    private String url(String s){ return s.replace(" ", "%20"); }
}

