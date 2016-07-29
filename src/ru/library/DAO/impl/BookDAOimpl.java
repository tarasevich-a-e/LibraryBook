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
    public boolean addElement(Object o) {
        Book book = (Book) o;
        int result = 0;
        try {
            statement = connection.createStatement();

            String zapros = "INSERT INTO db_library.book (`author_b`, `datecorr_b`, `dateloadbd_b`, `name_b`, `release_b`, `type_b`) " +
                    "VALUE (" +
                    book.getAuthor_b() +
                    ",\"" + book.getDatecorr_b() + "\"" +
                    ",\"" + book.getDateloadbd_b() + "\"" +
                    "," + book.getName_b() +
                    "," +  book.getRelease_b() +
                    "," + book.getType_b() +
                    ")";

            result = statement.executeUpdate(zapros);
            statement.close();
        } catch (SQLException var5) { var5.printStackTrace(); }
        if(result != 0) {

            logF.writeLog(">BookDAOimpl: Добавлено записей - " + result);
            return true;
        }
        logF.writeLog(">BookDAOimpl: Запись не добавлена");
        return false;
    }

    @Override
    public boolean updateUser(String login, String pass, String status) {return false;}

    @Override
    public String queryRecord(Object o) {
        Book book = (Book) o;
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            statement = connection.createStatement();
            //String zapros = "SELECT * FROM db_library.user WHERE login_u = " + name;
            String zapros = "SELECT * FROM db_library.book WHERE ";
            if (book.getId_b() != -1){ zapros = zapros + "id_b=" + book.getId_b() + " AND ";}
            if (book.getName_b().equals("-1") == false){ zapros = zapros + " name_b=" + book.getName_b() + " AND ";}
            if (book.getAuthor_b().equals("-1") == false){ zapros = zapros + " author_b=" + book.getAuthor_b() + " AND ";}
            if (book.getRelease_b() != -1){ zapros = zapros + " release_b=" + book.getRelease_b() + " AND ";}
            if (book.getType_b() != -1){ zapros = zapros + " type_b=" + book.getType_b() + " AND ";}
            if (book.getDatecorr_b().equals("-1") == false){ zapros = zapros + " datecorr_b=" + book.getDatecorr_b() + " AND ";}
            if (book.getDateloadbd_b().equals("-1") == false){ zapros = zapros + " dateloadbd_b=" + book.getDateloadbd_b() + " AND ";}
            zapros = zapros.substring(0, zapros.length()-5);

            ResultSet resultSet = statement.executeQuery(zapros);

            while(resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("id_b"),
                        resultSet.getString("author_b"),
                        resultSet.getString("datecorr_b"),
                        resultSet.getString("dateloadbd_b"),
                        resultSet.getString("name_b"),
                        resultSet.getInt("release_b"),
                        resultSet.getByte("type_b")
                ));
            }

            statement.close();
        } catch (SQLException var5) { var5.printStackTrace(); }

        if(books.size() != 0) {
            Gson gson1 = new Gson();
            return gson1.toJson(books);
        } else { return null; }
    }
}
