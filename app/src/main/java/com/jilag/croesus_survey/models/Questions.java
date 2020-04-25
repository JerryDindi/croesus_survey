package com.jilag.croesus_survey.models;

public class Questions {

    int id;
    String name;
    String value;
    int surevy_id;

    public Questions() {
    }

    public Questions(int id, String name, String value, int surevy_id) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.surevy_id = surevy_id;
    }

    public Questions(String name, String value, int surevy_id) {
        this.name = name;
        this.value = value;
        this.surevy_id = surevy_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public int getSurevy_id() {
        return surevy_id;
    }

    public void setSurevy_id(int surevy_id) {
        this.surevy_id = surevy_id;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", surevy_id=" + surevy_id +
                '}';
    }
}
