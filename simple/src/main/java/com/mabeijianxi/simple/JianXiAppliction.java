package com.mabeijianxi.simple;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by mabeijianxi on 2016/2/24.
 */
public class JianXiAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    private void initImageLoader(Context context) {
        {
            ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
            config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();//不会在内存中缓存多个大小的图片
            config.diskCacheFileNameGenerator(new Md5FileNameGenerator());//为了保证图片名称唯一
            //内存缓存大小默认是：app可用内存的1/8
            config.tasksProcessingOrder(QueueProcessingType.LIFO);
            config.writeDebugLogs(); // Remove for release app
            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config.build());
        }
    }
}
