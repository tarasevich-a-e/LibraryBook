package ru.library.Services.impl;

import ru.library.DAO.impl.BiblioDAOimpl;
import ru.library.Services.Services;

/**
 * Created by atarasevich on 20.07.16.
 */
public class BiblioServiceimpl implements Services {

    @Override
    public String getElement(String zapros) {
        BiblioDAOimpl biblioDAO = new BiblioDAOimpl();
        biblioDAO.connectionToBD();
        String infoAboutBiblio = biblioDAO.queryInfoAboutBiblio();
        biblioDAO.disconnectWithBD();

        return infoAboutBiblio;
    }

    @Override
    public Boolean addElement(String newElement) {
        return null;
    }

    @Override
    public Boolean inspectionElement(String inspElement) {
        return null;
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
