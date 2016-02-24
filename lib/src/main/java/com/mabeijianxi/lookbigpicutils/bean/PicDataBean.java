package com.mabeijianxi.lookbigpicutils.bean;

import java.io.Serializable;

/**
 * Created by mabeijianxi on 2016/2/22.
 */
public class PicDataBean implements Serializable{
    public int height;
    public int width;
    public int x;
    public int y;
    //        原图
    public String imageBigUrl;
    //        缩略图
    public String smallImageUrl;
    public void setUrl(PicUrlBean picUrlBean){
        this.imageBigUrl =picUrlBean.imageBigUrl;
        this.smallImageUrl=picUrlBean.smallImageUrl;
    }
}
