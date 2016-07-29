package ru.library.Services.impl;

import ru.library.DAO.iDAO;
import ru.library.Entity.User;
import ru.library.Factory.FactoryDAO;
import ru.library.Services.Services;

import java.util.Date;

/**
 * Created by atarasevich on 22.07.16.
 */
public class NewsServiceimpl implements Services {

    @Override
    public String getElements(Object o) {
        return null;
    }

    @Override
    public String getAllElements() {
        iDAO newsDAO = FactoryDAO.getDAO("News");
        newsDAO.connectionToBD();
        String infoAboutAllNews = newsDAO.queryAllRecord();
        newsDAO.disconnectWithBD();

        return infoAboutAllNews;
    }

    @Override
    public Boolean addElement(Object o) {
        return null;
    }

    @Override
    public boolean inspectionElement(String inspElement) {
        return true;
    }

    @Override
    public Boolean editElement(String corrElement) {
        return null;
    }

    @Override
    public Boolean deleteElement(String corrElement) {
        return null;
    }

    @Override
    public boolean autorizationElement(String login, String pass, String status) {
        return false;
    }
}
