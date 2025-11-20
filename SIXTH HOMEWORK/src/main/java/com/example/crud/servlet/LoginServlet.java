package com.example.crud.servlet;

import com.example.crud.dao.UserDao;
import com.example.crud.util.PasswordUtil;
import com.example.crud.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String error = req.getParameter("error");
        String msg = req.getParameter("msg");
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Вход</title></head><body>");
        out.println("<h1>Вход</h1>");
        if(msg!=null) out.println("<p style='color:green;'>"+esc(msg)+"</p>");
        if(error!=null) out.println("<p style='color:red;'>"+esc(error)+"</p>");
        out.println("<form method='post' action='login'>");
        out.println("Логин: <input name='username'><br>");
        out.println("Пароль: <input type='password' name='password'><br>");
        out.println("<input type='submit' value='Войти'>");
        out.println("</form>");
        out.println("<p><a href='register'>Регистрация</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(isBlank(username) || isBlank(password)){ resp.sendRedirect("login?error="+url("Введите логин и пароль")); return; }
        var opt = userDao.findByUsername(username.trim());
        if(opt.isEmpty()){ resp.sendRedirect("login?error="+url("Неверные данные")); return; }
        User u = opt.get();
        if(!PasswordUtil.verify(password, u.getPasswordHash())){ resp.sendRedirect("login?error="+url("Неверные данные")); return; }
        HttpSession session = req.getSession(true);
        session.setAttribute("USER_ID", u.getId());
        session.setAttribute("USERNAME", u.getUsername());
        resp.sendRedirect("products");
    }

    private boolean isBlank(String s){ return s==null||s.trim().isEmpty(); }
    private String esc(String s){ return s==null?"":s.replace("<","&lt;").replace(">","&gt;"); }
    private String url(String s){ return s.replace(" ", "%20"); }
}

