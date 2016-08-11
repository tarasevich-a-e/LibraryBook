package ru.library.Services.impl;

import ru.library.DAO.iDAO;
import ru.library.Entity.User;
import ru.library.Factory.FactoryDAO;
import ru.library.Services.Services;

import java.util.ArrayList;


/**
 * Created by atarasevich on 22.07.16.
 */
public class UserServiceimpl implements Services {

    @Override
    public String getElements(Object o) {
        return null;
    }

    @Override
    public String getAllElements() {
        return null;
    }

    @Override
    public String addElement(Object o) {
        iDAO userDAO = FactoryDAO.getDAO("User");
        userDAO.connectionToBD();
        boolean flagAdd = userDAO.addElement(o);
        userDAO.disconnectWithBD();
        return String.valueOf(flagAdd);
    }

    @Override
    public boolean inspectionElement(String inspElement) {
        iDAO userDAO = FactoryDAO.getDAO("User");
        userDAO.connectionToBD();
        ArrayList<User> users = userDAO.getValue(inspElement);
        if(users.size() != 0) {
            if(users.get(0).isStatus_u().equals("true")){
                userDAO.disconnectWithBD();
                return true;
            }
        }
        userDAO.disconnectWithBD();
        return false;
    }

    @Override
    public String editElement(Object o) {
        return null;
    }

    @Override
    public String deleteElement(String idElement) {
        return null;
    }

    @Override
    public User autorizationElement(String login, String pass, String status) {
        iDAO userDAO = FactoryDAO.getDAO("User");
        userDAO.connectionToBD();
        ArrayList<User> users = userDAO.getValue(login);
        boolean status_op = false;
        if (users.size() > 1){
            //Вывести в лог предупреждение о неуникальности логина
        }
        if(users.size() != 0) {
            if (status.equals("false")) {
                //Если пользователь выходит и он найден, возвращаем его в сервлет
                return users.get(0);
            } else {
                //Если пользователь пытается авторизоваться(заходит), то проверяем его пароль
                if (users.get(0).getPass_u().equals(pass)) {
                    //Если пароль верен, возвращаем пользователя в сервлет
                    return users.get(0);
                }
            }
        }

        userDAO.disconnectWithBD();
        return null;
    }
}
