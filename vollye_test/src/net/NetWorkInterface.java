package net;

import java.io.File;
import java.util.Map;

import org.json.JSONObject;

public interface NetWorkInterface {
	public void loginFamily(String url,Map<String,String> params);
	public void simpleStringRequest(String url,Map<String,String> params);
	public void loginOff(String url);
	public void getImage(String url);//½¨ÒéÊ¹ÓÃNetworkImageView
	public void fileUpload(String url,Map<String, File> files,Map<String, String> params);

}
