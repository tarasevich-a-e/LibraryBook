package ru.library;

import com.google.gson.Gson;
import ru.library.Entity.Book;
import ru.library.ToolsUserInterface.LogF;

import java.sql.*;
import java.util.ArrayList;

public class ZaprosBD {
    private final String USERNAME = "root";
    private final String PASS = "";
    private final String URL = "jdbc:mysql://localhost:3306/db_library";
    Connection connection;
    LogF logF = new LogF();
    Statement statement;

    public ZaprosBD() {
        //Инициализируем коннекшен с БД
        initBD();
    }

    public void initBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_library", "root", "");
            this.statement = this.connection.createStatement();
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

    public void destroyBD() {
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

    public String getJSON(String zapros) {
        ArrayList<Book> books = new ArrayList<Book>();

        try {
            this.statement = connection.createStatement();
            ResultSet resultSet = this.statement.executeQuery(zapros);

            while(resultSet.next()) {
                books.add(new Book(resultSet.getInt("id_b"), resultSet.getString("author_b"), resultSet.getString("name_b"), resultSet.getInt("release_b"), resultSet.getInt("type_b")));
            }

            resultSet.close();
            this.statement.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
        //Закрывает коннекшн с БД
        destroyBD();
        Gson gson1 = new Gson();
        return gson1.toJson(books);
    }
}
