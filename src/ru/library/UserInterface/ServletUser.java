package ru.library.UserInterface;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by atarasevich on 19.07.16.
 */
public class ServletUser extends HttpServlet {
    final static Logger logger = Logger.getLogger(ServletSingUp.class);


    public ServletUser() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher("/user.html").forward(req,resp);
    }
}
