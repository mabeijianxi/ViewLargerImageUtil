package com.mabeijianxi.simple;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mabeijianxi.viewlargerimage.ViewLargerImageUtil;
import com.mabeijianxi.viewlargerimage.bean.PicUrlBean;
import com.mabeijianxi.viewlargerimage.utils.CommonUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions config = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.home_youpin)
            .showImageOnFail(R.drawable.home_youpin)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .bitmapConfig(Bitmap.Config.RGB_565)//此种模消耗的内存会很小,2个byte存储一个像素
            .considerExifParams(true)
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gv_image);
        gridView.setAdapter(new JianXiAdapter());
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<PicUrlBean> picUrlList = new ArrayList<>();

        for(int i=0;i<UrlData.getSmallList().size();i++){
            PicUrlBean picUrlBean = new PicUrlBean();
            picUrlBean.imageBigUrl=UrlData.getBigUrlList().get(i);
            picUrlBean.smallImageUrl=UrlData.getSmallList().get(i);
            picUrlList.add(picUrlBean);
        }

        ViewLargerImageUtil.lookBigPic(MainActivity.this,view,picUrlList,position,4,4,3,true);
    }

    class JianXiAdapter extends BaseAdapter{

        @Override
        public int getCount() {

            return UrlData.getBigUrlList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(MainActivity.this,110));
            imageView.setLayoutParams(layoutParams);

            mImageLoader.displayImage(UrlData.getSmallList().get(position),imageView,config);
            return imageView;
        }
    }
}
