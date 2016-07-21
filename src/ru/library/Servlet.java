package ru.library;

import ru.library.Services.impl.BiblioServiceimpl;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {

    public Servlet() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();
        ZaprosBD zaprosBD = new ZaprosBD();
        printWriter.print(zaprosBD.getJSON("SELECT * FROM db_library.book"));
        printWriter.flush();

        BiblioServiceimpl biblioService = new BiblioServiceimpl();
        biblioService.getBiblioInfoJSON();
    }
}
