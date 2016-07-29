package ru.library.Factory;

import ru.library.Services.Services;
import ru.library.Services.impl.BiblioServiceimpl;
import ru.library.Services.impl.BookServiceimpl;
import ru.library.Services.impl.NewsServiceimpl;
import ru.library.Services.impl.UserServiceimpl;
import ru.library.ToolsUserInterface.LogF;

/**
 * Created by atarasevich on 21.07.16.
 */
public class FactoryService {
    static LogF logF;

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
                logF.writeLog("Service " + nameFunction + "отсутствует!");
                return null;
        }

    }

}
