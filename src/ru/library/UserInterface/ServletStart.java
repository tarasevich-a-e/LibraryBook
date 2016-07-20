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
public class ServletStart extends HttpServlet{
    LogF logF = new LogF();

    public ServletStart() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logF.createLog();
        logF.writeLog("Сервлет ServletStart работает!");
        logF.close();
    }
}
