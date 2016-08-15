package ru.library.DAO.impl;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.library.DAO.iDAO;
import ru.library.Entity.Biblio;
import ru.library.Entity.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by atarasevich on 20.07.16.
 */
public class BiblioDAOimpl implements iDAO {
    Connection connection;
    Statement statement;
    final static Logger logger = Logger.getLogger(BiblioDAOimpl.class);
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
    ///////////////////////////////////Получение информации о библиотеке из БД//////////////////////////////////////////
    @Override
    public String queryAllRecord() {
        ArrayList<Biblio> biblio = new ArrayList<Biblio>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.biblio";

            loggerDAO.info("Query :" + zapros);
            ResultSet resultSet = statement.executeQuery(zapros);

            while(resultSet.next()) {
                biblio.add(new Biblio(
                        resultSet.getString("history_b"),
                        resultSet.getString("adress_b"),
                        resultSet.getString("director_b"),
                        resultSet.getString("worktime_b")
                ));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        Gson gson1 = new Gson();
        return gson1.toJson(biblio);
    }

    @Override
    public ArrayList<User> getValue(String name) {
        return null;
    }

    @Override
    public boolean addElement(Object o) {
        return false;
    }

    @Override
    public boolean updateUser(String login, String pass, String status) {return false;}

    @Override
    public String queryRecord(Object o) {
        return null;
    }

    @Override
    public boolean updateElement(Object o) { return false; }

    @Override
    public boolean deleteElement(String idElement) {
        return false;
    }
}
