package com.example.deep.blappjsonobject;


import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.util.LruCache;
public class Mysingleton {
    ImageLoader mimageloader;
    private static Mysingleton minstance;
    private RequestQueue requestQueue;
    private static Context mctx;


    public Mysingleton(Context context) {
        mctx=context;
        requestQueue=getRequestQueue();
        mimageloader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
cache.put(url,bitmap);
            }
        });
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return requestQueue;
    }



    public static synchronized Mysingleton getInstance(Context context)
    {
        if(minstance==null)
        {
            minstance=new Mysingleton(context);
        }
        return minstance;
    }
    public void addToRequestQueue(Request request){
        requestQueue.add(request);
    }
    public ImageLoader getImageLoader(){
        return mimageloader;
    }


}