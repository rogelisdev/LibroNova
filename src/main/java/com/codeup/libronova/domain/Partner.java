package com.codeup.libronova.domain;

public class Partner {
    private int id;
    private String code_partner;
    private String fullname;
    private String phonenumber;
    private boolean statu_partner;

    public Partner(){};
    public Partner(String code_partner, String fullname, String phonenumber, boolean statu_partner) {
        this.code_partner = code_partner;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.statu_partner = statu_partner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode_partner() {
        return code_partner;
    }

    public void setCode_partner(String code_partner) {
        this.code_partner = code_partner;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isStatu_partner() {
        return statu_partner;
    }

    public void setStatu_partner(boolean statu_partner) {
        this.statu_partner = statu_partner;
    }
}
