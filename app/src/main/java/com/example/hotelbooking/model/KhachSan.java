package com.example.hotelbooking.model;

import java.io.Serializable;

public class KhachSan implements Serializable {
    public int ID;
    public String Tenks;
    public Integer Giaks;
    public String Hinhanhks;
    public String Mota;
    public int IDks;
    public String Diachi;

    public KhachSan(int ID, String tenks, Integer giaks, String hinhanhks, String mota, int IDks, String diachi) {
        this.ID = ID;
        Tenks = tenks;
        Giaks = giaks;
        Hinhanhks = hinhanhks;
        Mota = mota;
        this.IDks = IDks;
        Diachi = diachi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenks() {
        return Tenks;
    }

    public void setTenks(String tenks) {
        Tenks = tenks;
    }

    public Integer getGiaks() {
        return Giaks;
    }

    public void setGiaks(Integer giaks) {
        Giaks = giaks;
    }

    public String getHinhanhks() {
        return Hinhanhks;
    }

    public void setHinhanhks(String hinhanhks) {
        Hinhanhks = hinhanhks;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public int getIDks() {
        return IDks;
    }

    public void setIDks(int IDks) {
        this.IDks = IDks;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }
}
