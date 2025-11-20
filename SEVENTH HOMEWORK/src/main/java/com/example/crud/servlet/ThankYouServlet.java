package com.example.crud.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name="ThankYouServlet", urlPatterns={"/thankyou"})
public class ThankYouServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/thankyou.jsp").forward(req,resp);
    }
}

