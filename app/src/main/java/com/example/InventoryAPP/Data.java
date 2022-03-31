package com.example.InventoryAPP;

public class Data {
    private String id, kdbrg, nmbrg, hrgbeli, hrgjual, stok;

    public Data() {
    }

    public Data(String id, String kdbrg, String nmbrg, String hrgbeli, String hrgjual, String stok) {
        this.id = id;
        this.kdbrg = kdbrg;
        this.nmbrg = nmbrg;
        this.hrgbeli = hrgbeli;
        this.hrgjual = hrgjual;
        this.stok = stok;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKdbrg() {
        return kdbrg;
    }

    public void setKdbrg(String kdbrg) {
        this.kdbrg = kdbrg;
    }

    public String getNmbrg() {
        return nmbrg;
    }

    public void setNmbrg(String nmbrg) {
        this.nmbrg = nmbrg;
    }

    public String getHrgbeli() {
        return hrgbeli;
    }

    public void setHrgbeli(String hrgbeli) {
        this.hrgbeli = hrgbeli;
    }

    public String getHrgjual() {
        return hrgjual;
    }

    public void setHrgjual(String hrgjual) {
        this.hrgjual = hrgjual;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }
}
