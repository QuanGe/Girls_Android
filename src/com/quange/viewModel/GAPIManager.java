package com.quange.viewModel;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quange.model.GQiuBaiModel;

public class GAPIManager {
	private static GAPIManager sharedInstance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context theContext;
    private GAPIManager(Context context) {
    	theContext = context;
    	requestQueue = getRequestQueue();

    	imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }

                });

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
					List<GQiuBaiModel> result = new Gson().fromJson(body,
							new TypeToken<List<GQiuBaiModel>>() {
							}.getType());
					
					listener.onResponse(result);
					
				} catch (Exception e) {
					
				}
				
			}
		},errorListener);
		addToRequestQueue(request);
    }
    
    
}
