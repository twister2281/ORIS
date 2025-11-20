package com.example.crud.servlet;

import com.example.crud.dao.UserDao;
import com.example.crud.model.User;
import com.example.crud.util.PasswordUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet {
    private final UserDao userDao=new UserDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String username=req.getParameter("username"); String password=req.getParameter("password");
        if(blank(username)||blank(password)){ req.setAttribute("error","Введите логин и пароль"); req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,resp); return; }
        var opt=userDao.findByUsername(username.trim());
        if(opt.isEmpty()){ req.setAttribute("error","Неверные данные"); req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,resp); return; }
        User u=opt.get();
        if(!PasswordUtil.verify(password,u.getPasswordHash())){ req.setAttribute("error","Неверные данные"); req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,resp); return; }
        HttpSession session=req.getSession(true); session.setAttribute("USER_ID",u.getId()); session.setAttribute("USERNAME",u.getUsername());
        resp.sendRedirect("products");
    }
    private boolean blank(String s){ return s==null||s.trim().isEmpty(); }
}

