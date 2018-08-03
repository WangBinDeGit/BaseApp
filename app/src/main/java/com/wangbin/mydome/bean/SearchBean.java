package com.wangbin.mydome.bean;

import java.io.Serializable;

/**
 * Created by  wangbin.
 * on 2018/7/17
 */

public class SearchBean implements Serializable {

    private String name;

    private boolean isFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}
