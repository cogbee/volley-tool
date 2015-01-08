package user;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

public class User {
	
	public static UserInfo userInfo = new UserInfo();
	public static NetworkResponse net;
	public static RequestQueue mVolleyQueue;
	public static RequestQueue mSingleQueue;
	public static DefaultHttpClient mHttpClient;
	public static boolean isLogin = false;
	public static JSONObject response = new JSONObject();
	public static StringRequest myReq;
	public static String flag[] = {"false"};
	
}
