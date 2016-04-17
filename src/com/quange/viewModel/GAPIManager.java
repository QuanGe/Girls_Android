package com.quange.viewModel;

import java.util.List;


import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.quange.girls.R;
import com.quange.model.GQiuBaiModel;

public class GAPIManager {
	private static GAPIManager sharedInstance;
    private RequestQueue requestQueue;
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    private static Context theContext;
    public static DisplayImageOptions options;
    private GAPIManager(final Context context) {
    	theContext = context;
    	requestQueue = getRequestQueue();

    	initImageLoader();
		new Thread() {
			public void run() {
				initImageLoader(context);
			}
		}.start();
    	
    }
    
    public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option,
		// you may tune some of them, or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this); method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

	}

	void initImageLoader() {
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher).showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(true).cacheOnDisc(true)
				// .displayer(new RoundedBitmapDisplayer(20))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		
	}

    public static synchronized GAPIManager getInstance(Context context) {
        if (sharedInstance == null) {
        	sharedInstance = new GAPIManager(context);
        }
        return sharedInstance;
    }
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
        	requestQueue = Volley.newRequestQueue(theContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    
    public void fetchQiuBai(int pageNum,int type,final Listener<List<GQiuBaiModel>> listener, ErrorListener errorListener)
    {
    	 String api = "latest";
		switch (type) {
		case 0:
		    api = "latest";
		    break;
		case 1:
		    api = "imgrank";
		    break;
		case 2:
		    api = "suggest";
		    break;
		}
		StringRequest request = new StringRequest("http://m2.qiushibaike.com/article/list/"+api+"?page="+pageNum, new Listener<String>() {
			public void onResponse(String body) {
				try {
					JSONObject jsObj = new JSONObject(body);
					
					String r = jsObj.getString("items");
					List<GQiuBaiModel> result = new Gson().fromJson(r,
							new TypeToken<List<GQiuBaiModel>>() {
							}.getType());
					
					listener.onResponse(result);
					
				} catch (Exception e) {
					System.out.println("caocaocaocaocao");
				}
				
			}
		},errorListener);
		addToRequestQueue(request);
    }
    
    
}
