package ru.library.Entity;

/**
 * Created by atarasevich on 20.07.16.
 */
public class Biblio {
    private String history_b;
    private String adress_b;
    private String director_b;
    private String worktime_b;

    /////////////// Constructor ///////////////////
    public Biblio(String history_b, String adress_b, String director_b, String worktime_b) {
        this.history_b = history_b;
        this.adress_b = adress_b;
        this.director_b = director_b;
        this.worktime_b = worktime_b;
    }

    /////////////////// SET ///////////////////////
    public void setHistory_b(String history_b) { this.history_b = history_b; }

    public void setAdress_b(String adress_b) { this.adress_b = adress_b; }

    public void setDirector_b(String director_b) { this.director_b = director_b; }

    public void setWorktime_b(String worktime_b) { this.worktime_b = worktime_b; }

    /////////////////// GET ///////////////////////
    public String getHistory_b() { return history_b; }

    public String getAdress_b() { return adress_b; }

    public String getDirector_b() { return director_b; }

    public String getWorktime_b() { return worktime_b; }
}
