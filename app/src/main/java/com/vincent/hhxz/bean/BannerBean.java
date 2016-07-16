package com.vincent.hhxz.bean;

/**
 * Created by CJ on 2016/6/30.
 */
public class BannerBean {


    /**
     * @type : com.mobile.api.network.model.BannerInfo
     * logo : http://www.hehe168.com/public/upload/m/201603/01/56d55904ce35f.jpg
     * logo2 : http://www.hehe168.com/public/upload/m/201603/01/56d55904ce72c.jpg
     * type : 2
     * name : 抵抗力军团
     * desc : 抵抗力军团
     * url : http://ai.taobao.com/album/detail.htm?albumId=32255&pid=mm_35674402_8540341_28984811
     */

    private String logo;
    private String logo2;
    private String type;
    private String name;
    private String desc;
    private String url;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo2() {
        return logo2;
    }

    public void setLogo2(String logo2) {
        this.logo2 = logo2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
