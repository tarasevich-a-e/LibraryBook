package ru.library.DAO.impl;

import com.google.gson.Gson;
import ru.library.DAO.iDAO;
import ru.library.Entity.Biblio;
import ru.library.Entity.User;
import ru.library.ToolsUserInterface.LogF;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by atarasevich on 20.07.16.
 */
public class BiblioDAOimpl implements iDAO {
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
    public boolean getValue(String name) {
        return false;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }
}
