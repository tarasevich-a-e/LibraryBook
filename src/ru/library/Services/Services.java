package ru.library.Services;

import ru.library.Entity.User;

import java.util.Date;

/**
 * Created by atarasevich on 20.07.16.
 */
public interface Services {

    public String getElements(Object o);
    public String getAllElements();
    public String addElement(Object o);
    public boolean inspectionElement(String inspElement);
    public User autorizationElement(String login, String pass, String status);
    public String editElement(Object o);
    public String deleteElement(String idElement);
}
