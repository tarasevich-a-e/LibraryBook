package ru.library.DAO;

import ru.library.Entity.Biblio;
import ru.library.Entity.User;

import java.util.ArrayList;

/**
 * Created by atarasevich on 20.07.16.
 */
public interface iDAO {

    public void connectionToBD();
    public void disconnectWithBD();
    public String queryAllRecord();
    public String queryRecord(Object o);
    public boolean getValue(String name);
    public boolean addElement(Object o);
    public boolean updateElement(Object o);
    public boolean updateUser(String login, String pass, String status);
    public boolean deleteElement(String idElement);
}
