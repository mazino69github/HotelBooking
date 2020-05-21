package com.example.hotelbooking.model;

public class Loaiks {
    public int id;
    public String tenloaiks;
    public String hinhanhloaiks;

    public Loaiks(int id, String tenloaiks, String hinhanhloaiks) {
        this.id = id;
        this.tenloaiks = tenloaiks;
        this.hinhanhloaiks = hinhanhloaiks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaiks() {
        return tenloaiks;
    }

    public void setTenloaiks(String tenloaiks) {
        this.tenloaiks = tenloaiks;
    }

    public String getHinhanhloaiks() {
        return hinhanhloaiks;
    }

    public void setHinhanhloaiks(String hinhanhloaiks) {
        this.hinhanhloaiks = hinhanhloaiks;
    }
}
