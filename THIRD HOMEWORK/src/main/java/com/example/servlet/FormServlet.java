package com.example.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "FormServlet", urlPatterns = {"/form"})
public class FormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String error = request.getParameter("error");
        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head><meta charset='UTF-8'><title>Форма</title></head>");
        out.println("<body>");
        out.println("<h1>Регистрация / Отправка сообщения</h1>");
        if (error != null && !error.isBlank()) {
            out.println("<p style='color:red;'>" + escapeHtml(error) + "</p>");
        }
        out.println("<form method='post' action='form'>");
        out.println("Логин: <input name='login' type='text'/><br/>");
        out.println("Email: <input name='email' type='text'/><br/>");
        out.println("Пароль: <input name='password' type='password'/><br/>");
        out.println("Сообщение: <textarea name='message'></textarea><br/>");
        out.println("<input type='submit' value='Отправить'/>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String login = trimOrNull(request.getParameter("login"));
        String email = trimOrNull(request.getParameter("email"));
        String password = trimOrNull(request.getParameter("password"));
        String message = trimOrNull(request.getParameter("message"));

        String error = ValidationUtil.validate(login, email, password, message);
        if (error != null) {
            response.sendRedirect("form?error=" + urlEncode(error));
            return;
        }

        File dataDir = new File(getServletContext().getRealPath("/WEB-INF/data"));
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        File outFile = new File(dataDir, "submissions.txt");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, true), StandardCharsets.UTF_8))) {
            writer.write("Time=" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n");
            writer.write("Login=" + login + "\n");
            writer.write("Email=" + email + "\n");
            writer.write("Password=" + password + "\n");
            writer.write("Message=" + message + "\n");
            writer.write("----\n");
        }
        response.sendRedirect("thankyou");
    }

    private String trimOrNull(String s) { return s == null ? null : s.trim(); }
    private String escapeHtml(String s) { return s == null ? "" : s
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;"); }
    private String urlEncode(String s) { try { return URLEncoder.encode(s, StandardCharsets.UTF_8.name()); } catch (Exception e) { return s; } }
}
