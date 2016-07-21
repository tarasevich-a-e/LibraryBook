package ru.library.DAO;

import ru.library.Entity.Biblio;

import java.util.ArrayList;

/**
 * Created by atarasevich on 20.07.16.
 */
public interface BiblioDAO {

    public void connectionToBD();
    public void disconnectWithBD();
    public String queryInfoAboutBiblio();
}
