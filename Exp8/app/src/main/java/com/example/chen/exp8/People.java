package com.example.chen.exp8;

import java.io.Serializable;

/**
 * Created by Chen on 2016/11/20.
 */

public class People implements Serializable{
    private String name, birthday, gift;

    People(String name, String birthday, String gift) {
        this.name = name;
        this.birthday = birthday;
        this.gift = gift;
    }

    public String getName() {
        return  name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGift() {
        return gift;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
