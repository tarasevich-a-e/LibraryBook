package ru.library.Entity;

import java.util.Date;

public class Book {
    private int id_b;
    private String author_b;
    private String datecorr_b;
    private String dateloadbd_b;
    private String name_b;
    private int release_b;
    private int type_b;

    /////////////// Constructor ///////////////////
    public Book(int id_b, String author_b, String datecorr_b, String dateloadbd_b, String name_b, int release_b, int type_b) {
        this.id_b = id_b;
        this.author_b = author_b;
        this.datecorr_b = datecorr_b;
        this.dateloadbd_b = dateloadbd_b;
        this.name_b = name_b;
        this.release_b = release_b;
        this.type_b = type_b;
    }

    /////////////////// SET ///////////////////////
    public void setName_b(String name_b) { this.name_b = name_b; }

    public void setAuthor_b(String author_b) { this.author_b = author_b; }

    public void setRelease_b(int release_b) { this.release_b = release_b; }

    public void setType_b(int type_b) { this.type_b = type_b; }

    public void setDatecorr_b(String datecorr_b) { this.datecorr_b = datecorr_b; }

    public void setDateloadbd_b(String dateloadbd_b) { this.dateloadbd_b = dateloadbd_b; }

    /////////////////// GET ///////////////////////
    public int getId_b() {
        return this.id_b;
    }

    public String getName_b() {
        return this.name_b;
    }

    public String getAuthor_b() {
        return this.author_b;
    }

    public int getRelease_b() {
        return this.release_b;
    }

    public int getType_b() {
        return this.type_b;
    }

    public String getDatecorr_b() { return datecorr_b; }

    public String getDateloadbd_b() { return dateloadbd_b; }
}
