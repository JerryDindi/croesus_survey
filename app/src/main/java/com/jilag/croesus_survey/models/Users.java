package com.jilag.croesus_survey.models;

public class Users {

    int id;
    int id_num;
    String fname;
    String lname;
    String p_pic;

    public Users() {
    }

    public Users(int id, int id_num, String fname, String lname, String p_pic) {
        this.id = id;
        this.id_num = id_num;
        this.fname = fname;
        this.lname = lname;
        this.p_pic = p_pic;
    }

    public Users( int id_num, String fname, String lname, String p_pic) {
        this.id_num = id_num;
        this.fname = fname;
        this.lname = lname;
        this.p_pic = p_pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_num() {
        return id_num;
    }

    public void setId_num(int id_num) {
        this.id_num = id_num;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getP_pic() {
        return p_pic;
    }

    public void setP_pic(String p_pic) {
        this.p_pic = p_pic;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", id_num=" + id_num +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", p_pic='" + p_pic + '\'' +
                '}';
    }
}
