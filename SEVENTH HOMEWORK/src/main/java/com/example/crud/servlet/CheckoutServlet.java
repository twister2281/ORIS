package com.example.crud.servlet;

import com.example.crud.dao.ProductDao;
import com.example.crud.model.Product;
import com.example.crud.service.CartIds;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;

@WebServlet(name="CheckoutServlet", urlPatterns={"/checkout"})
public class CheckoutServlet extends HttpServlet {
    private final ProductDao dao=new ProductDao();
    @Override
    protected void doPost(HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false); if(session==null||session.getAttribute("USER_ID")==null){ resp.sendRedirect("login"); return; }
        CartIds cart=(CartIds)session.getAttribute("CART_IDS"); if(cart==null||cart.isEmpty()){ resp.sendRedirect("cart"); return; }
        File dir=new File(getServletContext().getRealPath("/WEB-INF/orders")); if(!dir.exists()) dir.mkdirs(); File file=new File(dir,"orders.txt"); BigDecimal total=BigDecimal.ZERO;
        try(Writer w=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), StandardCharsets.UTF_8))){
            w.write("OrderTime="+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"\n");
            for(var e:cart.getAll().entrySet()){ Product p=dao.findById(e.getKey()).orElse(null); if(p==null) continue; BigDecimal line=p.getPrice().multiply(BigDecimal.valueOf(e.getValue())); total=total.add(line); w.write(e.getKey()+"|"+p.getName()+"|"+e.getValue()+"|"+line+"\n"); }
            w.write("TOTAL="+total+"\n----\n");
        }
        cart.clear(); resp.sendRedirect("thankyou");
    }
}

