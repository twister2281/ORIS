package com.example.cart.servlet;

import com.example.cart.model.CartItem;
import com.example.cart.service.Cart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Cart cart = session == null ? null : (Cart) session.getAttribute("CART");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect("cart");
            return;
        }
        File dataDir = new File(getServletContext().getRealPath("/WEB-INF/orders"));
        if (!dataDir.exists()) dataDir.mkdirs();
        File outFile = new File(dataDir, "orders.txt");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, true), StandardCharsets.UTF_8))) {
            writer.write("OrderTime=" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n");
            for (CartItem item : cart.getItems()) {
                writer.write(item.getProduct().getId() + "|" + item.getProduct().getName() + "|" + item.getQuantity() + "|" + item.getTotal() + "\n");
            }
            writer.write("TOTAL=" + cart.getTotal() + "\n----\n");
        }
        session.removeAttribute("CART");
        resp.sendRedirect("thankyou");
    }
}

