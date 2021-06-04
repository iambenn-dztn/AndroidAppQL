package com.example.project_ql.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.sql.Blob;

public class SanPham implements Serializable {
    int ma, soluong, gianhap, giaban;
    String ten, nhaphanphoi, ghichu;
    byte[] anh;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public SanPham(int ma, String ten, int soluong, int gianhap, int giaban, String nhaphanphoi, String ghichu, byte[] anh) {
        this.ma = ma;
        this.soluong = soluong;
        this.gianhap = gianhap;
        this.giaban = giaban;
        this.nhaphanphoi = nhaphanphoi;
        this.ghichu = ghichu;
        this.ten= ten;
        this.anh=anh;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGianhap() {
        return gianhap;
    }

    public void setGianhap(int gianhap) {
        this.gianhap = gianhap;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public String getNhaphanphoi() {
        return nhaphanphoi;
    }

    public void setNhaphanphoi(String nhaphanphoi) {
        this.nhaphanphoi = nhaphanphoi;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
