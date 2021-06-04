package com.example.project_ql.model;

public class NoNsx {
    String ngay;
    int tien;

    public NoNsx(String ngay, int tien) {
        this.ngay = ngay;
        this.tien = tien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }
}
