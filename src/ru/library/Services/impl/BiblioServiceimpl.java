package ru.library.Services.impl;

import ru.library.DAO.impl.BiblioDAOimpl;
import ru.library.Entity.Biblio;
import ru.library.Services.BiblioService;

import java.util.ArrayList;

/**
 * Created by atarasevich on 20.07.16.
 */
public class BiblioServiceimpl implements BiblioService {
    //private BiblioDAOimpl biblioDAO;


    @Override
    public String getBiblioInfoJSON() {
        BiblioDAOimpl biblioDAO = new BiblioDAOimpl();
        biblioDAO.connectionToBD();
        String infoAboutBiblio = biblioDAO.queryInfoAboutBiblio();
        biblioDAO.disconnectWithBD();

        return infoAboutBiblio;
    }
}
