package ru.library.Entity;

public class Book {
    private int id_b;
    private String name_b;
    private String author_b;
    private int release_b;
    private int type_b;

    public Book(int id_b, String name_b, String author_b, int release_b, int type_b) {
        this.id_b = id_b;
        this.name_b = name_b;
        this.author_b = author_b;
        this.release_b = release_b;
        this.type_b = type_b;
    }

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
}
