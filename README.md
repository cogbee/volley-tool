# volley-tool

I do some work on volley.and add some lock to sysnc the request.

the response you can get at User.response.and it is a JSONObject.

use example:

1、post login for your 

NetHelper netHelper = new NetHelper();

User.mHttpClient = new DefaultHttpClient();

User.mVolleyQueue = Volley.newRequestQueue(this,new HttpClientStack(User.mHttpClient));

Map<String,String> params = new HashMap<String,String>();

User.userInfo.setUsername("123456");

User.userInfo.setPassword("123456");

params.put("j_username",User.userInfo.getUsername());

params.put("j_password",User.userInfo.getPassword());

String url = "www.baidu.com";

netHelper.loginFamily(url,params);


the response you can get at User.response.and it is a JSONObject.


2、upload files


Map<String, File> files = new HashMap<String, File>();

files.put("file", new File(

	"/wifi/ar6000.ko"));
	
	files.put("file", new File("/wifi/cfg80211.ko"));
	
Map<String, String> params = new HashMap<String, String>();

	params.put("token", "DJrlPbpJQs21rv1lP41yiA==");
	
	String uri = "yourUpload.php";
	
//params 不能为空

netHelper.fileUpload(uri,files,params);
				
				


3、loadImage,I use NetworkImageView


ImageLoader imageLoader = new ImageLoader(User.mVolleyQueue, new BitmapCache());
 NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
networkImageView.setDefaultImageResId(R.drawable.ic_empty);
 networkImageView.setErrorImageResId(R.drawable.ic_error);
 networkImageView.setImageUrl("http://img.hb.aicdn.com/801db4e0cbe0b86e4d26418e2f071e2bc4dd6cfa1bd5f-TH8ACY_fw658", imageLoader);


 so almost I can handle all the request.
     

