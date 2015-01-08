package user;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {
	
	private String Username = "";
	private String Password = "";
	private String Cookies = "";
	private Map<String,String> userMap = new HashMap<String,String>();
	
	public Map<String,String> getMap()
	{
		return userMap;
	}
	
	
	public String getUsername()
	{
		return Username;
	}
	
	public String getPassword()
	{
		return Password;
	}
	
	public String getCookies()
	{
		return Cookies;
	}
	
	public void setUsername(String user)
	{
		Username = user;
	}
	public void setPassword(String pass)
	{
		Password = pass;
	}
	public void setCookies(String cookies)
	{
		Cookies = cookies;
	}

}
