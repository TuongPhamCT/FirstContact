package com.example.buoi6contact;

import androidx.core.app.ActivityCompat;

import java.io.Serializable;

public class DanhBa implements Serializable {
    private String name;
    private String numberPhone;

    public String getName() {
        return name;
    }

    public DanhBa() {
    }

    public DanhBa(String name, String numberPhone) {
        this.name = name;
        this.numberPhone = numberPhone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
