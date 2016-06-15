package com.mabeijianxi.lookbigpicutils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mabeijianxi.lookbigpicutils.activty.LookBigPicActivity;
import com.mabeijianxi.lookbigpicutils.bean.PicDataBean;
import com.mabeijianxi.lookbigpicutils.bean.PicUrlBean;
import com.mabeijianxi.lookbigpicutils.utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabeijianxi on 2016/2/24.
 */
public class LookBigPicUtil {
    private static final int DEFAULT_ROW=3;

    /**
     * 大图查看，动态位置动画，需要封装 {@link com.mabeijianxi.lookbigpicutils.bean.PicUrlBean} 的list
     @param context
      * @param image 被点击的view
     * @param picUrlDataList
     * @param currentItem 需要打开第几张
     * @param horizontalPadding 图片之间的水平间距px
     * @param verticalPadding 图片之间的水平间距px
     * @param maxRow 最大的列数
     * @param isListPicShow 在点击跳转前的页面是列表展示的还是单图的形式
     */
    public static void lookBigPic(Context context,View image,List<PicUrlBean> picUrlDataList,int currentItem,int horizontalPadding,int verticalPadding,int maxRow,boolean isListPicShow) {
        Intent intent = new Intent(context, LookBigPicActivity.class);
        Bundle bundle = new Bundle();

        List<PicDataBean> picDataHavaCoords = new ArrayList<PicDataBean>();

        int xn = currentItem % maxRow + 1;
        int yn = currentItem / maxRow + 1;
        int h = (xn - 1) * CommonUtils.dip2px(context, horizontalPadding);
        int v = (yn - 1) * CommonUtils.dip2px(context, verticalPadding);
        int height = image.getHeight();
        int width = image.getWidth();
        int[] points = new int[2];
        image.getLocationInWindow(points);
        int x0 = points[0] - (width + h) * (xn - 1);
        int y0 = points[1] - (height + v) * (yn - 1);
//        给所有图片添加坐标信息
        for (int i = 0; i < picUrlDataList.size(); i++) {
            PicDataBean picDataBean =new  PicDataBean();
            PicUrlBean picUrlBean = picUrlDataList.get(i);

            picDataBean.setUrl(picUrlBean);
            picDataBean.width = width;
            picDataBean.height = height;
            if(isListPicShow) {
                picDataBean.x = x0 + (i % maxRow) * (width + CommonUtils.dip2px(context, horizontalPadding));//依次为每张图赋值，其实就累加而已
                picDataBean.y = y0 + (i / maxRow) * (height + CommonUtils.dip2px(context, verticalPadding)) - CommonUtils.getStatusBarHeight(image);
            }
            else{
                picDataBean.x = x0;
                picDataBean.y=y0- CommonUtils.getStatusBarHeight(image);
            }
                picDataHavaCoords.add(picDataBean);
        }


        bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable) picDataHavaCoords);
        intent.putExtras(bundle);
        intent.putExtra(LookBigPicActivity.CURRENTITEM, currentItem);
        context.startActivity(intent);
    }
    /**
     * 大图查看，动态位置动画，需要封装 {@link com.mabeijianxi.lookbigpicutils.bean.PicUrlBean} 的list
     * 默认图片列表小于等于三行三列,如需要指定请调用重载
     * 默认是个跳转前是个多张展示的列表
     * @param context
     * @param image 被点击的view
     * @param picUrlDataList
     * @param currentItem 需要打开第几张
     * @param horizontalPadding 图片之间的横向间距px
     * @param verticalPadding 图片之间的纵向间距px
     */
    public static void lookBigPic(Context context,View image,List<PicUrlBean> picUrlDataList,int currentItem,int horizontalPadding,int verticalPadding) {
        lookBigPic(context,image,picUrlDataList,currentItem,horizontalPadding,verticalPadding,DEFAULT_ROW,true);
    }
}
