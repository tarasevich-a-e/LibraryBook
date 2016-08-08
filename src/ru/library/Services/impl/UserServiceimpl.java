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
    public String getElements(Object o) {
        return null;
    }

    @Override
    public String getAllElements() {
        return null;
    }

    @Override
    public Boolean addElement(Object o) {
        iDAO userDAO = FactoryDAO.getDAO("User");
        userDAO.connectionToBD();
        boolean flagAdd = userDAO.addElement(o);
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
    public Boolean editElement(Object o) {
        return null;
    }

    @Override
    public Boolean deleteElement(String idElement) {
        return null;
    }

    @Override
    public boolean autorizationElement(String login, String pass, String status) {
        iDAO userDAO = FactoryDAO.getDAO("User");
        userDAO.connectionToBD();
        boolean status_op = userDAO.updateUser(login, pass, status);
        userDAO.disconnectWithBD();
        return status_op;
    }
}
