package com.spinoffpyme.qrreader.Model;

import java.util.Date;

/**
 * Created by Tomás on 25/04/2018.
 */

public class Contador {
    private String name;
    private int entradas;
    private Date fecha;

    public Contador() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }
}
