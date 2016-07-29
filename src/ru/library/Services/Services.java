package ru.library.Services;

import ru.library.Entity.User;

import java.util.Date;

/**
 * Created by atarasevich on 20.07.16.
 */
public interface Services {

    public String getElement(String zapros);
    public String getAllElements();
    public Boolean addElement(User user);
    public boolean inspectionElement(String inspElement);
    public Boolean editElement(String corrElement);
    public Boolean deleteElement(String corrElement);
}
