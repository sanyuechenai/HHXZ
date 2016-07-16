package com.vincent.hhxz.bean;

/**
 * Created by CJ on 2016/7/5.
 */
public class GoodsBean {
    private String imgUrl;
    private String imgName;
    private String imgId;

    public GoodsBean(){

    }
    public GoodsBean(String imgUrl,String imgName,String imgId){
        this.imgUrl=imgUrl;
        this.imgName=imgName;
        this.imgUrl=imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
