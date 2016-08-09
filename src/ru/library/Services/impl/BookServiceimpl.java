package ru.library.Services.impl;

import ru.library.DAO.iDAO;
import ru.library.DAO.impl.BiblioDAOimpl;
import ru.library.DAO.impl.BookDAOimpl;
import ru.library.Entity.Book;
import ru.library.Entity.User;
import ru.library.Factory.FactoryDAO;
import ru.library.Services.Services;

import java.util.Date;

/**
 * Created by atarasevich on 21.07.16.
 */
public class BookServiceimpl implements Services {


    @Override
    public String getAllElements() {
        iDAO bookDAO = FactoryDAO.getDAO("Book");
        bookDAO.connectionToBD();
        String infoAboutAllBooks = bookDAO.queryAllRecord();
        bookDAO.disconnectWithBD();

        return infoAboutAllBooks;
    }

    @Override
    public String getElements(Object o) {
        iDAO bookDAO = FactoryDAO.getDAO("Book");
        bookDAO.connectionToBD();
        String infoAboutAllBooks = bookDAO.queryRecord(o);
        bookDAO.disconnectWithBD();

        return infoAboutAllBooks;
    }

    @Override
    public Boolean addElement(Object o) {
        iDAO bookDAO = FactoryDAO.getDAO("Book");
        bookDAO.connectionToBD();
        boolean flagAdd = bookDAO.addElement(o);
        bookDAO.disconnectWithBD();

        return flagAdd;
    }

    @Override
    public boolean inspectionElement(String inspElement) {
        return true;
    }

    @Override
    public Boolean editElement(Object o) {
        iDAO bookDAO = FactoryDAO.getDAO("Book");
        bookDAO.connectionToBD();
        boolean flagAdd = bookDAO.updateElement(o);
        bookDAO.disconnectWithBD();

        return flagAdd;
    }

    @Override
    public Boolean deleteElement(String idElement) {
        iDAO bookDAO = FactoryDAO.getDAO("Book");
        bookDAO.connectionToBD();
        boolean flagDelete = bookDAO.deleteElement(idElement);
        bookDAO.disconnectWithBD();

        return flagDelete;
    }

    @Override
    public User autorizationElement(String login, String pass, String status) { return null; }
}
