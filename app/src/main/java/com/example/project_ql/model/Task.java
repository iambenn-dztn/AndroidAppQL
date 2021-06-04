package com.example.project_ql.model;

public class Task {
    int stt;
    String name;
    boolean check;

    public Task(int stt, String name, boolean check) {
        this.stt = stt;
        this.name = name;
        this.check = check;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
