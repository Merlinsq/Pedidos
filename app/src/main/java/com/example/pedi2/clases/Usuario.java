package com.example.pedi2.clases;
public class Usuario {
    private int id;
    private String user;
    private String password;

    public Usuario(int id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
