package com.example.alarm_miniproject.views;

public class ItemBaothuc {
    String Gio;
    String trangthai;
    boolean check;

    public ItemBaothuc() {
    }

    public ItemBaothuc(String gio, String trangthai, boolean check) {
        Gio = gio;
        this.trangthai = trangthai;
        if(trangthai.equals("Da dat bao thuc"))
        {
            this.check=true;
        }
        else this.check=false;
    }

    public String getGio() {
        return Gio;
    }

    public void setGio(String gio) {
        Gio = gio;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
