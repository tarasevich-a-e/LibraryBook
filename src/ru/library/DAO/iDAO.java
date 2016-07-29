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
    public boolean getValue(String name);
    public boolean addUser(User user);
}
