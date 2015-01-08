package com.example.vollye_test;

import java.io.File;
import java.net.CookieStore;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import user.User;
import user.UserInfo;
import util.BitmapCache;



import net.MultiPartStack;
import net.NetHelper;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;




public class MainActivity extends Activity {
	JSONObject test1 = new JSONObject(); 
	//private RequestQueue mVolleyQueue;
	NetHelper netHelper = new NetHelper();
	private static final int LOGIN_SUCCESS = 0;
	private static final int LOGIN_UNSUCCESS = 1;
	private SharedPreferences settings;	
	Map<String,String> params = new HashMap<String,String>();
	
	public String r;
	//userInfo一直在内存保存

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button test = (Button)findViewById(R.id.test);
        Button test2 = (Button)findViewById(R.id.test2);
        settings=getSharedPreferences("setting",0);
        //初始化
        User.mHttpClient = new DefaultHttpClient();
        User.mVolleyQueue = Volley.newRequestQueue(this,new HttpClientStack(User.mHttpClient));
        User.mSingleQueue = Volley.newRequestQueue(this, new MultiPartStack());
        //String url;
       
        
        test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//if(!isNetworkConnected(this))
				//	Toast.makeText(getApplicationContext(), "网络不可用请检测联网状态", Toast.LENGTH_SHORT).show();
				String url = "your url";
				//Map<String,String> params = new HashMap<String,String>();

				Log.i("test","OK1");
				//test1 = netHelper.JsonPostRequest(url,mVolleyQueue);	
				//test1 = netHelper.GetRequest(url, mVolleyQueue);
				netHelper.loginFamily(url);
				
				synchronized(User.flag)
				{
					try {
						User.flag.wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
					Log.i("test","k is true");
					String ok;
					try {
						ok = User.response.getString("requestResult");
						Log.i("testok",ok);
						if (ok == "true")
						{
							//to test cookies is ok or not
							url = "your url";
							netHelper.simpleStringRequest(url,params);
							
							synchronized(User.flag)
							{
								try {
									User.flag.wait();
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							Log.i("test","dfdfasff");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				Log.i("test","end-not-login");
				Log.i("testcccc",User.userInfo.getCookies());
				
				
			}
		});
        
        
        
        test2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, File> files = new HashMap<String, File>();
				files.put("file", new File(
						"/wifi/ar6000.ko"));
				files.put("file", new File("/wifi/cfg80211.ko"));
				Map<String, String> params = new HashMap<String, String>();
				params.put("token", "DJrlPbpJQs21rv1lP41yiA==");
				String uri = "yoururl";
				//params 不能为空
				netHelper.fileUpload(uri,files,params);
				synchronized(User.flag)
	            {
	            	User.flag[0] = "true";
	            	User.flag.notify();
	            } 
				Log.i("test","upload end");
			}
        });

        //网络加载图片
        ImageLoader imageLoader = new ImageLoader(User.mVolleyQueue, new BitmapCache());
        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
        networkImageView.setDefaultImageResId(R.drawable.ic_empty);
        networkImageView.setErrorImageResId(R.drawable.ic_error);
        networkImageView.setImageUrl("http://img.hb.aicdn.com/801db4e0cbe0b86e4d26418e2f071e2bc4dd6cfa1bd5f-TH8ACY_fw658", imageLoader);
        
    }
    
    
    


   
}
