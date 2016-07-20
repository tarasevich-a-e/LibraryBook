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
public class ServletDeleteBook extends HttpServlet {
    LogF logF = new LogF();

    public ServletDeleteBook() {
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logF.createLog();
        logF.writeLog("Сервлет ServletDeleteBook работает!");
        logF.close();
    }
}
