package com.example.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ThankYouServlet", urlPatterns = {"/thankyou"})
public class ThankYouServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'><title>Спасибо!</title></head><body>");
        out.println("<h1>Спасибо! Данные получены.</h1>");
        out.println("<p><a href='form'>Вернуться к форме</a></p>");
        out.println("</body></html>");
    }
}

