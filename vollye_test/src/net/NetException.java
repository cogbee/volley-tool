
package net;
/**
 * <p>网络异常，访问服务器的时候如果返回状态码�?200则抛出此异常
 * 抛异常的意义在于可以传�?�，便于上层服务处理，如果仅仅返回一个Json格式的串不易处理</p>
 * @author Tian
 * @version $Id: NetException.java 2014-4-10 下午2:58:14 Tian $
 */
public class NetException extends Exception {
	
	/* serialVersionUID:TODO. */
	private static final long serialVersionUID = -5740445555424597822L;

	public NetException(){
		super();
	}
	
	public NetException(String msg){
		super(msg);
	}
}

