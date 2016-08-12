package ru.library.Factory;

import org.apache.log4j.Logger;
import ru.library.DAO.iDAO;
import ru.library.DAO.impl.BiblioDAOimpl;
import ru.library.DAO.impl.BookDAOimpl;
import ru.library.DAO.impl.NewsDAOimpl;
import ru.library.DAO.impl.UserDAOimpl;


/**
 * Created by atarasevich on 21.07.16.
 */
public class FactoryDAO {

    final static Logger logger = Logger.getLogger(FactoryDAO.class);

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
                logger.info("DAO с именем " + nameDAO + "отсутствует!");
                return null;
        }

    }

}
