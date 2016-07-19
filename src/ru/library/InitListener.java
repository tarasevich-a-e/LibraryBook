package ru.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import ru.library.LogF;

public class InitListener implements ServletContextListener {
    private final String USERNAME = "root";
    private final String PASS = "";
    private final String URL = "jdbc:mysql://localhost:3306/db_library";
    Connection connection = null;
    Statement statement = null;
    ServletContext context = null;
    LogF logF = new LogF();

    public InitListener() {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_library", "root", "");
            this.statement = this.connection.createStatement();
            this.context = servletContextEvent.getServletContext();
            this.context.setAttribute("connection", this.connection);
        } catch (InstantiationException var3) {
            var3.printStackTrace();
        } catch (IllegalAccessException var4) {
            var4.printStackTrace();
        } catch (ClassNotFoundException var5) {
            var5.printStackTrace();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        this.logF.createLog();
        this.logF.writeLog("Connect OK!");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException var3) {
                var3.printStackTrace();
            }

            this.logF.writeLog("Disconnect OK!");
            this.logF.close();
        }

    }
}
