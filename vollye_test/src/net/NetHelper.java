package net;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.json.JSONException;
import org.json.JSONObject;

import user.User;


import com.android.volley.AuthFailureError;
//import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.util.Log;

/*
 * NetWorkInterface �ӿڵ�ʵ�֡�������������ķ���ֵ����User.response���档һЩ�û��Ĳ���������Ҫ������USER.userinfo����
 */


public class NetHelper implements NetWorkInterface{
	
	
	Map<String,String> mHeaders = new HashMap<String,String>(1);
	Map<String,String> params = new HashMap<String,String>();
	/** 
	 * Method to set a cookie 
	 */  
	public void getCookie() {  
	    CookieStore cs = User.mHttpClient.getCookieStore();
	    User.userInfo.setCookies(cs.getCookies().toString());
	   
	}  
	
	//��½
	public void loginFamily(String url)
	{
		StringRequest myReq = new StringRequest(Method.POST,
               url,
               mLoginResonseListenerString,
               createMyLoginReqErrorListener()){
			//���ò�����
			 protected Map<String,String> getParams(){
		            Map<String,String> params = new HashMap<String, String>();
		            User.userInfo.setUsername("yourUser");
					User.userInfo.setPassword("yourPass");
					params.put("j_username",User.userInfo.getUsername());
					params.put("j_password",User.userInfo.getPassword());
		            return params;
		        }
			 
			 protected Response<String> parseNetworkResponse(NetworkResponse response) {
			        String parsed;
			        try {
			        	Log.i("test","test-login");
			        	User.net = response;
			            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			            try {
							User.response = new JSONObject(parsed);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
			            //ͬ�����ã���Ȼvolley���첽�ģ�������ܼ�ʱ
			            synchronized(User.flag)
			            {
			            	User.flag[0] = "true";
			            	User.flag.notify();
			            }  
			        }catch (UnsupportedEncodingException e) {
			            parsed = new String(response.data);
			        }
			        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
			    }
		};

		User.myReq = myReq;
		myReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		User.mVolleyQueue.add(myReq);
		Log.i("test","login OK");
	}
	
	/*
	 * string ����
	 */
	public void simpleStringRequest(String url,final Map<String,String> params)
	{
		//cookieΪ�գ�������User���档
		if(User.userInfo.getCookies() == "")
		{
			getCookie();
		}	
		StringRequest myReq = new StringRequest(Method.POST,
                url,
                mResonseListenerString,
                createMyReqErrorListener()){
			//����cookie��һ��volley�Լ����Ѿ������ˡ���������ֻ��Ϊ����һ��
			public Map<String,String> getHeaders() throws AuthFailureError {
				if(User.userInfo.getCookies() != "")
				     mHeaders.put("Cookie", User.userInfo.getCookies());
		        return mHeaders;
		    }
			//���ò���
			protected Map<String,String> getParams(){
	            return params;
	        }

			 protected Response<String> parseNetworkResponse(NetworkResponse response) {
			        String parsed;
			        try {
			        	Log.i("test","not login parse");
			            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			            //�������Ҳ����content��json��ʽ���͸�User
			            try {
							User.response = new JSONObject(parsed);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            synchronized(User.flag)
			            {
			            	User.flag[0] = "true";
			            	User.flag.notify();
			            }
    
			        }catch (UnsupportedEncodingException e) {
			            parsed = new String(response.data);
			        }
			        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
			    }
		};
		
		myReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		User.mVolleyQueue.add(myReq);
	}
	
	//login OK listener
	Listener<String> mLoginResonseListenerString = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			Log.i("test", " on response String(login)" + response.toString());
			
		}
	};
	//not login OK listener
	Listener<String> mResonseListenerString = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			Log.i("test", " on response String(not login)" + response.toString());
			
		}
	};

//not login error listener
    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { 
            	Log.i("test","error-for not login");
            }
        };
    }
    //login error listener
    private Response.ErrorListener createMyLoginReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Log.i("test","error-login");
            }
    };
    }

    
	@Override
	public void loginOff(String url) {
		// TODO Auto-generated method stub
		//cookieΪ�գ�������User���档
				if(User.userInfo.getCookies() == "")
				{
					getCookie();
				}	
				StringRequest myReq = new StringRequest(Method.POST,
		                url,
		                mResonseforLogoffListenerString,
		                createMyLoginoffReqErrorListener()){
					//����cookie��һ��volley�Լ����Ѿ������ˡ���������ֻ��Ϊ����һ��
					public Map<String,String> getHeaders() throws AuthFailureError {
						if(User.userInfo.getCookies() != "")
						     mHeaders.put("Cookie", User.userInfo.getCookies());
				        return mHeaders;
				    }
					//���ò���
					protected Map<String,String> getParams(){
			            return params;
			        }

					 protected Response<String> parseNetworkResponse(NetworkResponse response) {
					        String parsed;
					        try {
					        	Log.i("test","logoff parse");
					            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
					            //�������Ҳ����content��json��ʽ���͸�User
					            try {
									User.response = new JSONObject(parsed);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					            synchronized(User.flag)
					            {
					            	User.flag[0] = "true";
					            	User.flag.notify();
					            }
		    
					        }catch (UnsupportedEncodingException e) {
					            parsed = new String(response.data);
					        }
					        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
					    }
				};
				
				myReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
				User.mVolleyQueue.add(myReq);
	}

	//login OK listener
	Listener<String> mResonseforLogoffListenerString = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			Log.i("test", " on response String(logoff OK)" + response.toString());
			
		}
	};
	 private Response.ErrorListener createMyLoginoffReqErrorListener() {
	        return new Response.ErrorListener() {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	            	Log.i("test","error-loginoff");
	            }
	    };
	    }
	
	@Override
	public void getImage(String url) {
		// TODO Auto-generated method stub
		
	}

	//�ļ��ϴ�.ע�����ֶԡ�
	@Override
	public void fileUpload(String url, Map<String, File> files,Map<String, String> params) {
		// TODO Auto-generated method stub
		addPutUploadFileRequest(
				url,
				files, params, mResonseforfileUploadListenerString, mErrorforfileUploadListener);
	}
	
	public void addPutUploadFileRequest(final String url,
			final Map<String, File> files, final Map<String, String> params,
			final Listener<String> responseListener, final ErrorListener errorListener) {
		
		if (null == url || null == responseListener) {
			return;
		}

		MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
				Request.Method.POST, url, responseListener, errorListener) {

			@Override
			public Map<String, File> getFileUploads() {
				return files;
			}

			@Override
			public Map<String, String> getStringUploads() {
				return params;
			}
			
		};
		User.mSingleQueue.add(multiPartRequest);
		}
	
	//upload ok listener
	Listener<String> mResonseforfileUploadListenerString = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			Log.i("test", " on response String(login)" + response.toString());
			
		}
	};
	//upload error listener
	ErrorListener mErrorforfileUploadListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			if (error != null) {
				if (error.networkResponse != null)
					Log.i("test", " on response json(fileupload error) " + new String(error.networkResponse.data));
			}
		}
	};
	
	
	
}


