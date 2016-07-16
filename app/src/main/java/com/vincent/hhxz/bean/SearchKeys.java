package com.vincent.hhxz.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by CJ on 2016/7/2.
 */

@Table(name="searchkey",id="_id")
public class SearchKeys extends Model {

    @Column(name="key_word")
    private String keyWord;

    public SearchKeys(){

    }
    public SearchKeys(String name){
        keyWord=name;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
