package ru.library.DAO.impl;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.library.DAO.iDAO;
import ru.library.Entity.Book;
import ru.library.Entity.User;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by atarasevich on 21.07.16.
 */
public class BookDAOimpl implements iDAO {
    Connection connection;
    Statement statement;
    final static Logger logger = Logger.getLogger(BookDAOimpl.class);
    final static Logger loggerDAO = Logger.getLogger("file3");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////Соединение с БД//////////////////////////////////////////////////////
    @Override
    public void connectionToBD() {
        ////////////////////////////Получение connection с БД///////////////////////////////////////////////////////////
        logger.info("----------------------------------------------------");
        logger.info("Take me connection");
        long start = System.currentTimeMillis();
        connection = DBCoonectionPoll.getConnection();
        long finish = System.currentTimeMillis();
        logger.info("Time - " + (finish - start));

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////Закрываем соединение с БД/////////////////////////////////////////////////
    @Override
    public void disconnectWithBD() {
        DBCoonectionPoll.freeConnection(connection);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////Получение информации о книгах из БД///////////////////////////////////////////
    @Override
    public String queryAllRecord() {
        ArrayList<Book> books = new ArrayList<Book>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.book";

            loggerDAO.info("Query :" + zapros);
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
    public ArrayList<User> getValue(String name) {
        return null;
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

            loggerDAO.info("Query :" + zapros);
            result = statement.executeUpdate(zapros);
            statement.close();
        } catch (SQLException var5) { var5.printStackTrace(); }
        if(result != 0) {

            logger.info(">BookDAOimpl: Добавлено записей - " + result);
            return true;
        }
        logger.info(">BookDAOimpl: Запись не добавлена");
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

            //Конвертируем строковые поля с кавычками
            if(book.getName_b().charAt(0) == '"'){book.setName_b(book.getName_b().substring(1, book.getName_b().length() - 1));}
            if(book.getAuthor_b().charAt(0) == '"'){book.setAuthor_b(book.getAuthor_b().substring(1, book.getAuthor_b().length() - 1));}
            if(book.getDatecorr_b().charAt(0) == '"'){book.setDatecorr_b(book.getDatecorr_b().substring(1, book.getDatecorr_b().length() - 1));}
            if(book.getDateloadbd_b().charAt(0) == '"'){book.setDateloadbd_b(book.getDateloadbd_b().substring(1, book.getDateloadbd_b().length() - 1));}

            //String zapros = "SELECT * FROM db_library.user WHERE login_u = " + name;
            String zapros = "SELECT * FROM db_library.book WHERE ";

            if (book.getId_b() != -1){ zapros = zapros + "id_b =" + book.getId_b() + " AND ";}
            if (book.getName_b().equals("-1") == false){ zapros = zapros + " name_b LIKE '%" + book.getName_b() + "%' AND ";}
            if (book.getAuthor_b().equals("-1") == false){ zapros = zapros + " author_b LIKE '%" + book.getAuthor_b() + "%' AND ";}
            if (book.getRelease_b() != -1){ zapros = zapros + " release_b LIKE '%" + book.getRelease_b() + "%' AND ";}
            if (book.getType_b() != -1){ zapros = zapros + " type_b LIKE '%" + book.getType_b() + "%' AND ";}
            if (book.getDatecorr_b().equals("-1") == false){ zapros = zapros + " datecorr_b LIKE '%" + book.getDatecorr_b() + "%' AND ";}
            if (book.getDateloadbd_b().equals("-1") == false){ zapros = zapros + " dateloadbd_b LIKE '%" + book.getDateloadbd_b() + "%' AND ";}

            /*if (book.getId_b() != -1){ zapros = zapros + "id_b=" + book.getId_b() + " AND ";}
            if (book.getName_b().equals("-1") == false){ zapros = zapros + " name_b=" + book.getName_b() + " AND ";}
            if (book.getAuthor_b().equals("-1") == false){ zapros = zapros + " author_b=" + book.getAuthor_b() + " AND ";}
            if (book.getRelease_b() != -1){ zapros = zapros + " release_b=" + book.getRelease_b() + " AND ";}
            if (book.getType_b() != -1){ zapros = zapros + " type_b=" + book.getType_b() + " AND ";}
            if (book.getDatecorr_b().equals("-1") == false){ zapros = zapros + " datecorr_b=" + book.getDatecorr_b() + " AND ";}
            if (book.getDateloadbd_b().equals("-1") == false){ zapros = zapros + " dateloadbd_b=" + book.getDateloadbd_b() + " AND ";}*/
            zapros = zapros.substring(0, zapros.length()-5);

            loggerDAO.info("Query :" + zapros);
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

    @Override
    public boolean updateElement(Object o) {
        Book book = (Book) o;
        int result = 0;
        try {
            statement = connection.createStatement();

            //Конвертируем строковые поля с кавычками
            if(book.getName_b().charAt(0) == '"'){book.setName_b(book.getName_b().substring(1, book.getName_b().length() - 1));}
            if(book.getAuthor_b().charAt(0) == '"'){book.setAuthor_b(book.getAuthor_b().substring(1, book.getAuthor_b().length() - 1));}
            if(book.getDatecorr_b().charAt(0) == '"'){book.setDatecorr_b(book.getDatecorr_b().substring(1, book.getDatecorr_b().length() - 1));}
            if(book.getDateloadbd_b().charAt(0) == '"'){book.setDateloadbd_b(book.getDateloadbd_b().substring(1, book.getDateloadbd_b().length() - 1));}

            String zapros = "UPDATE db_library.book SET ";
                    if(book.getAuthor_b().equals("-1") == false){zapros = zapros + "author_b=\"" + book.getAuthor_b() + "\",";}
                    zapros = zapros + "datecorr_b=\"" + book.getDatecorr_b() + "\",";
                    if(book.getName_b().equals("-1") == false){zapros = zapros + "name_b=\"" + book.getName_b() + "\",";}
                    if(book.getRelease_b() != -1){zapros = zapros +"release_b=\"" + book.getRelease_b() + "\",";}
                    if(book.getType_b() != -1){zapros = zapros + "type_b=\"" + book.getType_b() + "\",";}
                    zapros = zapros.substring(0,zapros.length()-1);
                    zapros = zapros + " WHERE id_b=" + book.getId_b();

            loggerDAO.info("Query :" + zapros);
            result = statement.executeUpdate(zapros);
            statement.close();
        } catch (SQLException var5) { var5.printStackTrace(); }
        if(result != 0) {

            logger.info(">BookDAOimpl: Изменено записей - " + result);
            return true;
        }
        logger.info(">BookDAOimpl: Запись не изменена");
        return false;
    }

    @Override
    public boolean deleteElement(String idElement) {
        int result = 0;
        try {
            statement = connection.createStatement();
            String zapros = "DELETE FROM db_library.book WHERE id_b=" + idElement;

            loggerDAO.info("Query :" + zapros);
            result = statement.executeUpdate(zapros);
            statement.close();
        } catch (SQLException var5) { var5.printStackTrace(); }
        if(result != 0) {

            logger.info(">BookDAOimpl: Удалено записей - " + result);
            return true;
        }
        logger.info(">BookDAOimpl: Запись не удалена");
        return false;
    }
}
