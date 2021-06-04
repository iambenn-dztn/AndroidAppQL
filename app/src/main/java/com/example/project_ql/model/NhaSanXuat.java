package com.example.project_ql.model;

public class NhaSanXuat {
    int ma;
    String ten;
    int conno;

    public NhaSanXuat(int ma, String ten, int conno) {
        this.ma = ma;
        this.ten = ten;
        this.conno = conno;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getConno() {
        return conno;
    }

    public void setConno(int conno) {
        this.conno = conno;
    }
}
