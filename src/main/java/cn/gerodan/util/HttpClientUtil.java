package cn.gerodan.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	/** 连接超时时间 */
	private final static int CONN_TIMEOUT = 10000;
	/** 请求超时时间 */
	private final static int REQUEST_TIMEOUT = 10000;
	/** 支持JSON、XML*/
	private final static String RETURN_FOMATE = "xml";
	/** 接口基础URL */
	public static final String BaseURL = "http://172.16.5.100/omm_spc"; 
	/** 登录基础URL */
	public static final String BaseLoginURL = HttpClientUtil.BaseURL+"/rest/session/sso?ticket="; 
	
	private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public HttpClient loginedClient =new HttpClient();
	
	
	public HttpClientUtil(String loginSSOTicket ) {
		super();
		this.loginedClient=doGetLogin(loginSSOTicket);
	}
	
	public HttpClientUtil() {
		super();
	}

	/**
	 * 请求服务器入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param map
	 *            上送输入参数集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String doPost(String loginSSOTicket,String url, Map<String, String> postParamKeyValue) {
		//先调用登录,获得Client对象
		HttpClient thisClient=doGetLogin(loginSSOTicket);
				
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		postMethod.setRequestBody(this.getPostParams(postParamKeyValue));
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				return new String(postMethod.getResponseBodyAsString());
			} else {
				result = new String(postMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * 请求服务器入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param map
	 *            上送输入参数集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String doPost(String url, Map<String, String> postParamKeyValue) {
		HttpClient thisClient= new HttpClient();
				
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		postMethod.setRequestBody(this.getPostParams(postParamKeyValue));
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				return new String(postMethod.getResponseBodyAsString());
			} else {
				result = new String(postMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	public String doPost(String loginSSOTicket,String url,Part[] parts) {
		//先调用登录,获得Client对象
		HttpClient thisClient=doGetLogin(loginSSOTicket);

		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Accept" , "text/html, application/xhtml+xml, */*") ;
		postMethod.setRequestHeader("Accept-Language" , "zh-CN") ;
		postMethod.setRequestHeader("UserAgent" , "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)") ;
		postMethod.setRequestHeader("Accept-Encoding" , "gzip, deflate") ;
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		postMethod.getParams().setContentCharset("UTF-8");
		thisClient.getParams().setContentCharset("UTF-8");
		
		try {
	    postMethod.setRequestEntity(new MultipartRequestEntity(parts,postMethod.getParams()));
		int statusCode = thisClient.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
			return new String(postMethod.getResponseBodyAsString());
		} else {
			result = new String(postMethod.getResponseBodyAsString());
		}
		} catch (Exception e) {
			log.debug("调用二级库异常"+e.toString());
			e.printStackTrace();
			return e.getMessage();
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * 请求服务器入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param map
	 *            上送输入参数集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("deprecation")
	public String doPost(String loginSSOTicket,String url,String xmlStr) {
		//先调用登录,获得Client对象
		HttpClient thisClient=doGetLogin(loginSSOTicket);
		
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-type" , "application/xml") ;
		postMethod.setRequestBody("<?xml version='1.0' encoding='UTF-8'?>"+xmlStr);
		postMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				return new String(postMethod.getResponseBodyAsString());
			} else {
				result = new String(postMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * 请求服务器入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param map
	 *            上送输入参数集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("deprecation")
	public String doPut(String loginSSOTicket,String url, String xml) {
		//先调用登录,获得Client对象
		HttpClient thisClient=doGetLogin(loginSSOTicket);
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		PutMethod putMethod = new PutMethod(url);
		putMethod.setRequestHeader("Content-type" , "text/xml") ;
		putMethod.setRequestBody("<?xml version='1.0' encoding='UTF-8'?>"+xml);
		putMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		putMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		putMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		putMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				return new String(putMethod.getResponseBodyAsString());
			} else {
				result = new String(putMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			putMethod.releaseConnection();
		}
		return result;
	}

	/**
	 * 请求服务器入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param map
	 *            上送输入参数集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String doPut(String url) {
		HttpClient thisClient = new HttpClient();
		
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		PutMethod putMethod = new PutMethod(url);
		putMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		putMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		putMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		putMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				return new String(putMethod.getResponseBodyAsString());
			} else {
				result = new String(putMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			putMethod.releaseConnection();
		}
		return result;
	}
	/**
	 * 请求服务器入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param map
	 *            上送输入参数集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String doPut(String loginSSOTicket,String url) {
		//先调用登录,获得Client对象
		HttpClient thisClient=doGetLogin(loginSSOTicket);
		
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		PutMethod putMethod = new PutMethod(url);
		putMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		putMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		putMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		putMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				return new String(putMethod.getResponseBodyAsString());
			} else {
				result = new String(putMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			putMethod.releaseConnection();
		}
		return result;
	}
	
	/**
	 * 请求服务器入口
	 * 
	 * @param methodName
	 *            方法名
	 * @param map
	 *            上送输入参数集合
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String doDelete(String url) {
		HttpClient thisClient=new HttpClient();
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT); // 连接超时设置
		DeleteMethod deleteMethod = new DeleteMethod(url);
		deleteMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		deleteMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		deleteMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); // post请求超时设置
		deleteMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(deleteMethod);
			// 如果请求返回的不是成功，则进行处理
			if (statusCode != HttpStatus.SC_OK) {
				return new String(deleteMethod.getResponseBodyAsString());
			} else {
				result = new String(deleteMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			System.out.println(" 请提供正确的URL");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			deleteMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * Http Get方法
	 */
	public String doGet(String loginSSOTicket,String url) {
		//先调用登录,获得Client对象
		HttpClient thisClient=doGetLogin(loginSSOTicket);
		
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT);
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); 
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				log.debug("Get执行失败"+statusCode);
				return null;
			} else {
				result = new String(getMethod.getResponseBodyAsString());
				log.debug("Get执行成功,结果为:"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * Http Get方法
	 */
	public String doGet(String url) {
		HttpClient thisClient = new HttpClient();
		String result = null;
		thisClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT);
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); 
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = thisClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				log.debug("Get执行失败"+statusCode);
				return null;
			} else {
				result = new String(getMethod.getResponseBodyAsString());
				log.debug("Get执行成功,结果为:"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}
	
	/*
	 * Http Get通用登录方法
	 */
	public HttpClient doGetLogin(String ticket) {
		String result = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT);
		GetMethod getMethod = new GetMethod(HttpClientUtil.BaseLoginURL+ticket);
		getMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); 
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				log.debug("二级库登录失败"+statusCode);
				return null;
			} else {
				result = new String(getMethod.getResponseBodyAsString());
				log.debug("二级库登录成功"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			getMethod.releaseConnection();
		}
		return httpClient;
	}
	
	/*
	 * Http Get通用登录方法返回登录成功Cookie
	 */
	public Cookie[] doGetLoginCookie(String ticket) {
		String result = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT);
		GetMethod getMethod = new GetMethod(HttpClientUtil.BaseLoginURL+ticket);
		getMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); 
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				log.debug("二级库登录失败"+statusCode);
				return null;
			} else {
				result = new String(getMethod.getResponseBodyAsString());
				log.debug("二级库登录成功"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			getMethod.releaseConnection();
		}
		return httpClient.getState().getCookies();
	}
	
	/*
	 * Http Get方法
	 */
	public byte[] doGetPic(Cookie[] loginedCookie,String url) {
		byte[] result = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getState().addCookies(loginedCookie);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT);
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); 
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				log.debug("Get执行失败"+statusCode);
				return null;
			} else {
				result = getMethod.getResponseBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}	
	
	/*
	 * Http Get方法
	 */
	public String doGetByCookie(Cookie[] loginedCookie,String url) {
		String result = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getState().addCookies(loginedCookie);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONN_TIMEOUT);
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Connection" , "Keep-Alive") ;
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, REQUEST_TIMEOUT); 
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {// 如果请求返回的不是成功，则进行处理
				log.debug("Get执行失败"+statusCode);
				return null;
			} else {
				result = new String(getMethod.getResponseBodyAsString());
				log.debug("Get执行成功,结果为:"+result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}
	/**
	 * 查询-键值对
	 * @param map
	 * @return
	 */
	private NameValuePair[] getPostParams(Map<String, String> map) {
		map.put("_type", RETURN_FOMATE);
		NameValuePair[] data = new NameValuePair[map.size()];
		Iterator<String> it = map.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			data[i] = new NameValuePair(key, value);
			i++;
		}
		return data;
	}
	
}