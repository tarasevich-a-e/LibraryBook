package ru.library.Entity;

import java.util.Date;

/**
 * Created by atarasevich on 20.07.16.
 */
public class User {
    private int id_u;
    private String dr_u;
    private String famil_u;
    private String login_u;
    private String name_u;
    private String otch_u;
    private String pass_u;
    private int rol_u;
    private boolean status_u;
    private byte usercol;

    /////////////// Constructor ///////////////////


    public User(int id_u, String dr_u, String famil_u, String login_u, String name_u, String otch_u, String pass_u, int rol_u, boolean status_u, byte usercol) {
        this.id_u = id_u;
        this.dr_u = dr_u;
        this.famil_u = famil_u;
        this.login_u = login_u;
        this.name_u = name_u;
        this.otch_u = otch_u;
        this.pass_u = pass_u;
        this.rol_u = rol_u;
        this.status_u = status_u;
        this.usercol = usercol;
    }

    /////////////////// SET ///////////////////////
    public void setFamil_u(String famil_u) { this.famil_u = famil_u; }

    public void setName_u(String name_u) { this.name_u = name_u; }

    public void setOtch_u(String otch_u) { this.otch_u = otch_u; }

    public void setDr_u(String dr_u) { this.dr_u = dr_u; }

    public void setLogin_u(String login_u) { this.login_u = login_u; }

    public void setPass_u(String pass_u) { this.pass_u = pass_u; }

    public void setStatus_u(boolean status_u) { this.status_u = status_u; }

    public void setRol_u(int rol_u) { this.rol_u = rol_u; }

    public void setUsercol(byte usercol) { this.usercol = usercol; }

    /////////////////// GET ///////////////////////
    public int getId_u() { return id_u; }

    public String getFamil_u() { return famil_u; }

    public String getName_u() { return name_u; }

    public String getOtch_u() { return otch_u; }

    public String getDr_u() { return dr_u; }

    public String getLogin_u() { return login_u; }

    public String getPass_u() { return pass_u; }

    public boolean isStatus_u() { return status_u; }

    public int getRol_u() { return rol_u; }

    public byte getUsercol() { return usercol; }
}
