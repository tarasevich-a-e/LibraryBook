package ru.library.Services.impl;

import ru.library.DAO.iDAO;
import ru.library.Entity.User;
import ru.library.Factory.FactoryDAO;
import ru.library.Services.Services;


/**
 * Created by atarasevich on 22.07.16.
 */
public class UserServiceimpl implements Services {

    @Override
    public String getElement(String zapros) {
        return null;
    }

    @Override
    public String getAllElements() {
        return null;
    }

    @Override
    public Boolean addElement(User user) {
        iDAO userDAO = FactoryDAO.getDAO("User");
        userDAO.connectionToBD();
        boolean flagAdd = userDAO.addUser(user);
        userDAO.disconnectWithBD();
        return flagAdd;
    }

    @Override
    public boolean inspectionElement(String inspElement) {
        iDAO userDAO = FactoryDAO.getDAO("User");
        userDAO.connectionToBD();
        boolean status = userDAO.getValue(inspElement);
        userDAO.disconnectWithBD();
        return status;
    }

    @Override
    public Boolean editElement(String corrElement) {
        return null;
    }

    @Override
    public Boolean deleteElement(String corrElement) {
        return null;
    }
}
