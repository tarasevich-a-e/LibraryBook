package ru.library.Entity;

import java.util.Date;

/**
 * Created by atarasevich on 20.07.16.
 */
public class News {
    private int id_n;
    private String text_n;
    private Date date_n;   //Протестировать

    /////////////// Constructor ///////////////////
    public News(int id_n, String text_n, Date date_n) {
        this.id_n = id_n;
        this.text_n = text_n;
        this.date_n = date_n;
    }

    /////////////////// GET ///////////////////////
    public int getId_n() { return id_n; }

    public String getText_n() { return text_n; }

    public Date getDate_n() { return date_n; }
}
