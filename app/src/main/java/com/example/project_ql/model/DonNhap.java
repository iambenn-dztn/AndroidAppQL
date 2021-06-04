package com.example.project_ql.model;

public class DonNhap {
    int ma;
    String tennsx,tensp,soluong;
    int tongtien,datra,conno;
    String ngay, ghichu;

    public DonNhap(int ma, String tennsx, String tensp, String soluong, int tongtien, int datra, int conno, String ngay, String ghichu) {
        this.ma = ma;
        this.tennsx = tennsx;
        this.tensp = tensp;
        this.soluong = soluong;
        this.tongtien = tongtien;
        this.datra = datra;
        this.conno = conno;
        this.ngay = ngay;
        this.ghichu = ghichu;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTennsx() {
        return tennsx;
    }

    public void setTennsx(String tennsx) {
        this.tennsx = tennsx;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getDatra() {
        return datra;
    }

    public void setDatra(int datra) {
        this.datra = datra;
    }

    public int getConno() {
        return conno;
    }

    public void setConno(int conno) {
        this.conno = conno;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
