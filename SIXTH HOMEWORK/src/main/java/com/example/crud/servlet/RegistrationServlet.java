package com.example.crud.servlet;

import com.example.crud.dao.UserDao;
import com.example.crud.util.PasswordUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="RegistrationServlet", urlPatterns={"/register"})
public class RegistrationServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String error = req.getParameter("error");
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Регистрация</title></head><body>");
        out.println("<h1>Регистрация</h1>");
        if(error!=null) out.println("<p style='color:red;'>"+esc(error)+"</p>");
        out.println("<form method='post' action='register'>");
        out.println("Логин: <input name='username'><br>");
        out.println("Пароль: <input type='password' name='password'><br>");
        out.println("Пароль ещё раз: <input type='password' name='password2'><br>");
        out.println("<input type='submit' value='Зарегистрироваться'>");
        out.println("</form>");
        out.println("<p><a href='login'>Уже есть аккаунт? Войти</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        if(isBlank(username) || isBlank(password) || isBlank(password2)) { redirectError(resp,"Все поля обязательны"); return; }
        if(!password.equals(password2)) { redirectError(resp,"Пароли не совпадают"); return; }
        if(password.length()<5){ redirectError(resp,"Пароль слишком короткий"); return; }
        if(userDao.findByUsername(username.trim()).isPresent()){ redirectError(resp,"Логин занят"); return; }
        userDao.create(username.trim(), PasswordUtil.hash(password));
        resp.sendRedirect("login?msg=registered");
    }

    private void redirectError(HttpServletResponse resp, String msg) throws IOException { resp.sendRedirect("register?error="+url(msg)); }
    private boolean isBlank(String s){ return s==null||s.trim().isEmpty(); }
    private String esc(String s){ return s==null?"":s.replace("<","&lt;").replace(">","&gt;"); }
    private String url(String s){ return s.replace(" ", "%20"); }
}

