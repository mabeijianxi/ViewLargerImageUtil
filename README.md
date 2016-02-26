# LookBigPicUtils

##大图查看器
####一个查看大图的库，只需要几行代码就可以实现。先看效果图
![demo](http://7xq6db.com1.z0.glb.clouddn.com/lookbigpic.gif)

## Usage

###Step-1
导入并且添加依赖：

Gradle:
`compile project(':lib')`

###Step-2

配置AndriodManifest:
######权限配置
```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
######配置Activity

```java
<activity
   android:name="com.mabeijianxi.lookbigpicutils.activty.LookBigPicActivity"
   android:theme="@android:style/Theme.Translucent.NoTitleBar"
   android:launchMode="singleTop" />
```

###Step-3

######初始化ImageLoader
```java
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
```
###Step-4

######在合适的地方使用，可参照simple

你只需要封装一个包含大图url与小图url即可：
 ```java
	ArrayList<PicUrlBean> picUrlList = new ArrayList<>();

        for(int i=0;i<UrlData.getSmallList().size();i++){
            PicUrlBean picUrlBean = new PicUrlBean();
            picUrlBean.imageBigUrl=UrlData.getBigUrlList().get(i);
            picUrlBean.smallImageUrl=UrlData.getSmallList().get(i);
            picUrlList.add(picUrlBean);
        }
```
然后直接跳转就完成了：

```java
        LookBigPicUtil.lookBigPic(MainActivity.this,view,picUrlList,position,4,4,3);
```

##有什么不足的地方欢迎大家提出，也很乐意和大家交流，我的联系邮箱是*mabeijianxi@gmail.com*