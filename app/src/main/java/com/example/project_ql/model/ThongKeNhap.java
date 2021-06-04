package com.example.project_ql.model;

public class ThongKeNhap {
    boolean check;
    String ngay, tennsx;
    int giatridon;

    public ThongKeNhap(boolean check, String ngay, String tennsx, int giatridon) {
        this.check = check;
        this.ngay = ngay;
        this.tennsx = tennsx;
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

    public String getTennsx() {
        return tennsx;
    }

    public void setTennsx(String tennsx) {
        this.tennsx = tennsx;
    }

    public int getGiatridon() {
        return giatridon;
    }

    public void setGiatridon(int giatridon) {
        this.giatridon = giatridon;
    }
}
