package ru.library.Entity;

import java.util.Date;

/**
 * Created by atarasevich on 20.07.16.
 */
public class News {
    private int id_n;
    private String date_n;
    private String text_n;


    /////////////// Constructor ///////////////////
    public News(int id_n, String date_n, String text_n) {
        this.id_n = id_n;
        this.date_n = date_n;
        this.text_n = text_n;
    }

    /////////////////// GET ///////////////////////
    public int getId_n() { return id_n; }

    public String getText_n() { return text_n; }

    public String getDate_n() { return date_n; }
}
