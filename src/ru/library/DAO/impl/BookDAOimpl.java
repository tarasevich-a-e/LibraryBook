package ru.library.DAO.impl;

import com.google.gson.Gson;
import ru.library.DAO.iDAO;
import ru.library.Entity.Biblio;
import ru.library.Entity.Book;
import ru.library.Entity.User;
import ru.library.ToolsUserInterface.LogF;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by atarasevich on 21.07.16.
 */
public class BookDAOimpl implements iDAO {
    Connection connection;
    Statement statement;
    LogF logF;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Соединение с БД//////////////////////////////////////////////////////
    @Override
    public void connectionToBD() {
        ////////////////////////////Получение параметров для подключения к БД///////////////////////////////////////////
        Properties properties = CommonMetodForDAO.getPropertyOfTheFile("Заглушка");

        ////////////////////////////Получение connection с БД///////////////////////////////////////////////////////////
        connection = CommonMetodForDAO.getConnection(
                properties.getProperty("driver.name"),
                properties.getProperty("db.URL"),
                properties.getProperty("db.USERNAME"),
                properties.getProperty("db.PASS")
        );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Закрываем соединение с БД/////////////////////////////////////////////////
    @Override
    public void disconnectWithBD() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException var3) { this.logF.writeLog("Connect is not close!"); var3.printStackTrace(); }
            this.logF.writeLog("Disconnect OK!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////Получение информации о книгах из БД///////////////////////////////////////////
    @Override
    public String queryAllRecord() {
        ArrayList<Book> books = new ArrayList<Book>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.book";
            ResultSet resultSet = statement.executeQuery(zapros);

            while(resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("id_b"),
                        resultSet.getString("author_b"),
                        resultSet.getString("datecorr_b"),
                        resultSet.getString("dateloadbd_b"),
                        resultSet.getString("name_b"),
                        resultSet.getInt("release_b"),
                        resultSet.getInt("type_b")
                ));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        Gson gson1 = new Gson();
        return gson1.toJson(books);
    }

    @Override
    public boolean getValue(String name) {
        return false;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }
}
