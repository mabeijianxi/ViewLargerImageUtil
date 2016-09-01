# ViewLargerImageUtil

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

######在合适的地方使用，可参照samples

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
        ViewLargerImageUtil.lookBigPic(MainActivity.this,view,picUrlList,position,4,4,3,true);
```
###原理：
######查看大图是实际开发中比较常见的，假设你是查看的一组的中的一张，如果要实现上面的效果首先你需要指定你查看的是第几张，并且你需要知道这张图的大小和坐标，并且你需要计算出这张图所在的组图里面所有的位置坐标，这样后续的动画才有办法进行。下面将一步一步分析。

#####1、获取组图中每张图片的信息并且封装：
都是搬砖的，都懂的，就直接上代码和注释了

```java

		
		int xn = currentItem % maxRow + 1;//当前点击的图片x方向是第几个（currentItem从0开始，xn从1开始，都是数学小原理，就不解释了）
        int yn = currentItem / maxRow + 1;//当前点击的图片y方向是第几个（currentItem从0开始，yn从1开始，都是数学小原理，就不解释了）

        int h = (xn - 1) * CommonUtils.dip2px(context, horizontalPadding);//当前点击的图片距离最左边的总Padding
        int v = (yn - 1) * CommonUtils.dip2px(context, verticalPadding);//当前点击的图片距离最上边的总Padding
		//接着先获取组图中第一张图片宽高与在屏幕上的坐标
        int height = image.getHeight();
        int width = image.getWidth();
        int[] points = new int[2];
        image.getLocationInWindow(points);
        int x0 = points[0] - (width + h) * (xn - 1);//组图中第一张图片的x坐标
        int y0 = points[1] - (height + v) * (yn - 1);//组图中第一张图片的y坐标
		//由于是规则的有了第一张图片的信息一切都变得简单了，接着就可以得到所有图片的信息
        for (int i = 0; i < picUrlDataList.size(); i++) {
            PicDataBean picDataBean =new  PicDataBean();
            PicUrlBean picUrlBean = picUrlDataList.get(i);

            picDataBean.setUrl(picUrlBean);
            picDataBean.width = width;
            picDataBean.height = height;
            
                picDataBean.x = x0 + (i % maxRow) * (width + CommonUtils.dip2px(context, horizontalPadding));//依次为每张图赋值，其实就累加而已
                picDataBean.y = y0 + (i / maxRow) * (height + CommonUtils.dip2px(context, verticalPadding)) - CommonUtils.getStatusBarHeight(image);//
           
                picDataList.add(picDataBean);
        }
```

#####2、在合适的时候开始Activity的出场动画：
那么问题来了，什么时候合适？我这里选择为imageview设置OnPreDrawListener，其回调时机顾名思义。接下来分析这个动画执行过程。这个动画是让图片从其在组图中的大小慢慢变为高充满全屏或者宽充满全屏（后面会说到计算规则），其位置也会从原来组图中所在的位置变到屏幕正中心，当然为了更酷可以可以为北京设置透明度的变化。大概流程就是这样，接着代码实践：

```java

 	public boolean onPreDraw() {
		
        viewPager.getViewTreeObserver().removeOnPreDrawListener(this);//只需要执行一次出场动画
        final View view = imageScaleAdapter.getPrimaryItem();
        final PhotoView imageView = (PhotoView) ((ViewGroup) view).getChildAt(0);//这个用了谷歌工程师Chris Banes写的PhotoView控件来显示图片

        Drawable drawable = imageView.getDrawable();
        int intrinsicHeight = drawable.getIntrinsicHeight();//获得图片实际的高度（可能是大图的，也可能是小图的，不重要）
        int intrinsicWidth = drawable.getIntrinsicWidth();//获得图片实际的宽度（可能是大图的，也可能是小图的，不重要）
        float h = CommonUtils.getScreenSizeHeight(this) * 1.0f / intrinsicHeight;
        float w = CommonUtils.getScreenSizeWidth(this) * 1.0f / intrinsicWidth;
		//这里是要比较屏幕高与图片高的比例与屏幕高=宽与图片宽的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满屏幕，就是宽度没充满屏幕
        if (h > w) {
            h = w;
        } else {
            w = h;
        }
        height = (int) (intrinsicHeight * h);//得到做完动画后显示到屏幕上的图片高
        width = (int) (intrinsicWidth * w);//得到做完动画后显示到屏幕上的图片宽

        final PicDataBean picBean = picDataList.get(mPositon);
        final float vx = picBean.width * 1.0f / width;//计算出做动画前的图片（小图）与做动画前的图片（大图）的比例
        final float vy = picBean.height * 1.0f / height;//计算出做动画前的图片（小图）与做动画前的图片（大图）的比例

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatedFraction = animation.getAnimatedFraction();

                view.setTranslationX(EvaluateUtil.evaluateInt(animatedFraction, picBean.x + picBean.width / 2 - imageView.getWidth() / 2, 0));//这个平移动画也很简单，确定三个东西即可完成一帧，就跟初中的指定起点与终点位置，求此路程1/2的位置一直，这里也是。x方向起点是 picBean.x + picBean.width / 2 - imageView.getWidth() / 2，至于终点这里也就是不偏移的时候，具体偏移值就是0了（对坐标计算不太敏感的童鞋可以画图看下）
                view.setTranslationY(EvaluateUtil.evaluateInt(animatedFraction, picBean.y + picBean.height / 2 - imageView.getHeight() / 2, 0));//Y方向与X方向同理
				//缩放这个还是比较有意思的，起点缩放比例是vx，终点比例自如是1也就是不缩放。
                view.setScaleX(EvaluateUtil.evaluateFloat(animatedFraction, vx, 1));
                view.setScaleY(EvaluateUtil.evaluateFloat(animatedFraction, vy, 1));
				//背景的透明过度，比较简单
                ll_root.setBackgroundColor((int) EvaluateUtil.evaluateArgb(animatedFraction, 0x0, 0xff000000));
            }
        });

        valueAnimator.setDuration(300);
        valueAnimator.start();
        return true;
    }
```

于是整个出场动画过程就完成了。
#####3、在需要关闭的时候开始Activity的退场动画：
有了出场动画退场动画就非常简单了，反过来就ok了

```java
   		
        final View view = imageScaleAdapter.getPrimaryItem();//得到当看到的View
        final PhotoView imageView = (PhotoView) ((ViewGroup) view).getChildAt(0);//得到当看到的PhotoView
        imageView.setZoomable(false);//      当图片被放大时，需要把其缩放回原来大小再做动画

         Drawable drawable = imageView.getDrawable();
        int intrinsicHeight = drawable.getIntrinsicHeight();//获得图片实际的高度（可能是大图的，也可能是小图的，不重要）
        int intrinsicWidth = drawable.getIntrinsicWidth();//获得图片实际的宽度（可能是大图的，也可能是小图的，不重要）
        float h = CommonUtils.getScreenSizeHeight(this) * 1.0f / intrinsicHeight;
        float w = CommonUtils.getScreenSizeWidth(this) * 1.0f / intrinsicWidth;
		//这里是要比较屏幕高与图片高的比例与屏幕高=宽与图片宽的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满屏幕，就是宽度没充满屏幕
        if (h > w) {
            h = w;
        } else {
            w = h;
        }
        height = (int) (intrinsicHeight * h);//得到做完动画后显示到屏幕上的图片高
        width = (int) (intrinsicWidth * w);//得到做完动画后显示到屏幕上的图片宽

        final PicDataBean ealuationPicBean = picDataList.get(mPositon);
        final float vx = ealuationPicBean.width * 1.0f / width;
        final float vy = ealuationPicBean.height * 1.0f / height;
		//退场动画过程只需要把入场对话反向就行了，so easy
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float animatedFraction = animation.getAnimatedFraction();

                view.setTranslationX(EvaluateUtil.evaluateInt(animatedFraction, 0, ealuationPicBean.x + ealuationPicBean.width / 2 - imageView.getWidth() / 2));
                view.setTranslationY(EvaluateUtil.evaluateInt(animatedFraction, 0, ealuationPicBean.y + ealuationPicBean.height / 2 - imageView.getHeight() / 2));
               
			    view.setScaleX(EvaluateUtil.evaluateFloat(animatedFraction, 1, vx));
                view.setScaleY(EvaluateUtil.evaluateFloat(animatedFraction, 1, vy));
                
				ll_root.setBackgroundColor((int) EvaluateUtil.evaluateArgb(animatedFraction, 0xff000000, 0x0));

                if (animatedFraction > 0.95) {
                    view.setAlpha(1 - animatedFraction);
                }
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
```

#####4、优化一些图片加载过程
在图片加载的时候肯定是需要时间的，这个时间的长短是根据网络情况和图片大小等等因素决定的，这个时候界面应该显示什么？进度条吗，我觉得不只是这样。用Fresco的话是支持渐进式的图片加载的，其实自己也能实现个类似的体验。方案很简单，加载大图的时候从内存中取小图的Bigmap，先显示小图和进度条，加载完成后再显示大图，看着就是从模糊慢慢变清晰。这里演示下用universalimageloader时的操作：

```java

	private void setupNetImage(final ProgressBar pb, final PicDataBean picBean) {
        mImageLoader.displayImage(picBean.imageBigUrl, imageView, pager_options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                startLoad(pb);//显示进度条 

                 Bitmap bitmap = ImageUtils.getBitmapFromCache(ealuationPicBean.smallImageUrl, mImageLoader);//，不能直接使用：imageLoader.getMemoryCache().get(uri)来获取，因为在加载过程中，key是经过运算的，而不单单是uri,而是： String memoryCacheKey = MemoryCacheUtil.generateKey(uri, targetSize)
		
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.defaultPic);
        }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                overLoad(pb);//结束进度条
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                overLoad(pb);//结束进度条
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                overLoad(pb);//结束进度条
            }
        });
    }
```

##到此一个漂亮的大图查看工具就搞定了
##有什么不足的地方欢迎大家提出，也很乐意和大家交流，我的联系邮箱是*mabeijianxi@gmail.com*
