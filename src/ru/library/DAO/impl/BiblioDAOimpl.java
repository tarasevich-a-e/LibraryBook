package ru.library.DAO.impl;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.library.DAO.iDAO;
import ru.library.Entity.Biblio;
import ru.library.Entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by atarasevich on 20.07.16.
 */
public class BiblioDAOimpl implements iDAO {
    Connection connection;
    Statement statement;
    final static Logger logger = Logger.getLogger(BiblioDAOimpl.class);

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
            } catch (SQLException var3) { this.logger.info("Connect is not close!"); var3.printStackTrace(); }
            this.logger.info("Disconnect OK!");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////Получение информации о библиотеке из БД//////////////////////////////////////////
    @Override
    public String queryAllRecord() {
        ArrayList<Biblio> biblio = new ArrayList<Biblio>();

        try {
            statement = connection.createStatement();
            String zapros = "SELECT * FROM db_library.biblio";
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
