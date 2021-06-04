package com.example.project_ql.model;

public class ThongKeXuat {
    boolean check;
    String ngay, tendaily;
    int giatridon;

    public ThongKeXuat(boolean check, String ngay, String tendaily, int giatridon) {
        this.check = check;
        this.ngay = ngay;
        this.tendaily = tendaily;
        this.giatridon = giatridon;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTendaily() {
        return tendaily;
    }

    public void setTendaily(String tendaily) {
        this.tendaily = tendaily;
    }

    public int getGiatridon() {
        return giatridon;
    }

    public void setGiatridon(int giatridon) {
        this.giatridon = giatridon;
    }
}
