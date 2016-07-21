package ru.library.UserInterface;

import ru.library.Services.impl.BiblioServiceimpl;
import ru.library.ToolsUserInterface.LogF;
import ru.library.ZaprosBD;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletStart extends HttpServlet{
    LogF logF = new LogF();

    public ServletStart() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logF.createLog();

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();
        BiblioServiceimpl biblioService = new BiblioServiceimpl();
        String infoAboutBiblio = biblioService.getBiblioInfoJSON();

        printWriter.print(infoAboutBiblio);
        printWriter.flush();

        logF.writeLog("Сервлет ServletStart работает!");
        logF.close();
    }
}
