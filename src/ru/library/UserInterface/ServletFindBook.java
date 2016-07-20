package ru.library.UserInterface;

import ru.library.LogF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletFindBook extends HttpServlet {
    LogF logF = new LogF();

    public ServletFindBook() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logF.createLog();
        logF.writeLog("Сервлет ServletFindBook работает!");
        logF.close();
    }
}
