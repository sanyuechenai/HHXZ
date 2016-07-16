package com.vincent.hhxz.bean;

/**
 * Created by CJ on 2016/7/4.
 */
public class SubListBean {


    /**
     * cat_id : 292
     * name : 全部
     * short_name :
     * logo : http://www.hehe168.com/public/upload/images/201505/01/554328ef45dcc.jpg
     * parent_id : 0
     * cover_logo : http://www.hehe168.com/public/upload/images/201512/26/567e4a461d1a4.jpg
     */

    private String cat_id;
    private String name;
    private String logo;
    private String cover_logo;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover_logo() {
        return cover_logo;
    }

    public void setCover_logo(String cover_logo) {
        this.cover_logo = cover_logo;
    }
}
