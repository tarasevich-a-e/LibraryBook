package ru.library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.library.ZaprosBD;

public class Servlet extends HttpServlet {
    public Servlet() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        Connection connection = (Connection)this.getServletContext().getAttribute("connection");
        String string = "";
        if(connection != null) {
            string = ", key3:\"OK\"";
        }

        PrintWriter printWriter = resp.getWriter();
        ZaprosBD zaprosBD = new ZaprosBD();
        printWriter.print(zaprosBD.getJSON("SELECT * FROM db_library.book", connection));
        printWriter.flush();
    }
}
