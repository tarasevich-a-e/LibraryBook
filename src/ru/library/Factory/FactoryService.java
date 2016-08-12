package ru.library.Factory;

import org.apache.log4j.Logger;
import ru.library.Services.Services;
import ru.library.Services.impl.BiblioServiceimpl;
import ru.library.Services.impl.BookServiceimpl;
import ru.library.Services.impl.NewsServiceimpl;
import ru.library.Services.impl.UserServiceimpl;

/**
 * Created by atarasevich on 21.07.16.
 */
public class FactoryService {
    final static Logger logger = Logger.getLogger(FactoryService.class);

    public static Services getService(String nameFunction) {
        switch (nameFunction) {
            case "Biblio":
                return new BiblioServiceimpl();
            case "Book":
                return new BookServiceimpl();
            case "User":
                return new UserServiceimpl();
            case "News":
                return new NewsServiceimpl();
            default:
                logger.info("Service " + nameFunction + "отсутствует!");
                return null;
        }

    }

}
