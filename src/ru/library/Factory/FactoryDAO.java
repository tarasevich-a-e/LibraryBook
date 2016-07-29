package ru.library.Factory;

import ru.library.DAO.iDAO;
import ru.library.DAO.impl.BiblioDAOimpl;
import ru.library.DAO.impl.BookDAOimpl;
import ru.library.DAO.impl.NewsDAOimpl;
import ru.library.DAO.impl.UserDAOimpl;
import ru.library.Services.Services;
import ru.library.Services.impl.BiblioServiceimpl;
import ru.library.Services.impl.BookServiceimpl;
import ru.library.Services.impl.NewsServiceimpl;
import ru.library.Services.impl.UserServiceimpl;
import ru.library.ToolsUserInterface.LogF;

/**
 * Created by atarasevich on 21.07.16.
 */
public class FactoryDAO {

    static LogF logF;

    public static iDAO getDAO(String nameDAO) {
        switch (nameDAO) {
            case "Biblio":
                return new BiblioDAOimpl();
            case "Book":
                return new BookDAOimpl();
            case "User":
                return new UserDAOimpl();
            case "News":
                return new NewsDAOimpl();
            default:
                logF.writeLog("DAO с именем " + nameDAO + "отсутствует!");
                return null;
        }

    }

}
