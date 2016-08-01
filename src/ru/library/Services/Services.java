package ru.library.Services;

import ru.library.Entity.User;

import java.util.Date;

/**
 * Created by atarasevich on 20.07.16.
 */
public interface Services {

    public String getElements(Object o);
    public String getAllElements();
    public Boolean addElement(Object o);
    public boolean inspectionElement(String inspElement);
    public boolean autorizationElement(String login, String pass, String status);
    public Boolean editElement(Object o);
    public Boolean deleteElement(String corrElement);
}
