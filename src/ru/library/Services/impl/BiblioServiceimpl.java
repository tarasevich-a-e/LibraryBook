package ru.library.Services.impl;

import ru.library.DAO.iDAO;
import ru.library.DAO.impl.BiblioDAOimpl;
import ru.library.Entity.User;
import ru.library.Factory.FactoryDAO;
import ru.library.Services.Services;

import java.util.Date;

/**
 * Created by atarasevich on 20.07.16.
 */
public class BiblioServiceimpl implements Services {

    @Override
    public String getElements(Object o) {

        return null;
    }

    @Override
    public String getAllElements() {
        iDAO biblioDAO = FactoryDAO.getDAO("Biblio");
        biblioDAO.connectionToBD();
        String infoAboutBiblio = biblioDAO.queryAllRecord();
        biblioDAO.disconnectWithBD();

        return infoAboutBiblio;
    }

    @Override
    public String addElement(Object o) {
        return null;
    }

    @Override
    public boolean inspectionElement(String inspElement) {
        return true;
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
        return null;
    }
}
