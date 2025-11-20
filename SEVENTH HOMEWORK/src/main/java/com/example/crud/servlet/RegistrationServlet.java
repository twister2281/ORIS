import java.io.IOException;

@WebServlet(name="RegistrationServlet", urlPatterns={"/register"})
public class RegistrationServlet extends HttpServlet {
    private final UserDao userDao=new UserDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String username=req.getParameter("username"); String password=req.getParameter("password"); String password2=req.getParameter("password2");
        if(blank(username)||blank(password)||blank(password2)){ req.setAttribute("error","Все поля обязательны"); req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req,resp); return; }
        if(!password.equals(password2)){ req.setAttribute("error","Пароли не совпадают"); req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req,resp); return; }
        if(password.length()<5){ req.setAttribute("error","Пароль слишком короткий"); req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req,resp); return; }
        if(userDao.findByUsername(username.trim()).isPresent()){ req.setAttribute("error","Логин занят"); req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req,resp); return; }
        userDao.create(username.trim(), PasswordUtil.hash(password));
        resp.sendRedirect("login?msg=registered");
    }
    private boolean blank(String s){ return s==null||s.trim().isEmpty(); }
}
package com.example.crud.servlet;

import com.example.crud.dao.UserDao;
import com.example.crud.util.PasswordUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

