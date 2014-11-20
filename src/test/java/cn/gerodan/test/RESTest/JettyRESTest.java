package cn.gerodan.test.RESTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.gerodan.util.HttpClientUtil;

public class JettyRESTest {

	private HttpClientUtil client = null;

	@Before
	public void setUp() throws Exception {
		client = new HttpClientUtil();
	}

	@Test
	public void restPost() {
		//新增请求(POST)
		String url="http://localhost:8080/v1/fs/files";
		Map<String,String> paraMap=new HashMap<String,String>();
		paraMap.put("fileID", "fdfdagdgegha");
		paraMap.put("desc", "一个文件");
		paraMap.put("size", "304893");
		paraMap.put("shards", "3");
		String result = client.doPost(url, paraMap);
		System.out.println(result);
	}
	
	@Test
	public void restGet() {
		//查询请求(Get)
		String key="008";
		String url="http://localhost:8080/v1/fs/files/"+key;
		String result = client.doGet(url);
		System.out.println(result);
	}
	
	@Test
	public void restDelete() {
		String filleID="54tjiruifjifeuf";
		//添加请求(Put)
		String url="http://localhost:8080/v1/fs/files/"+filleID;
		String result = client.doDelete(url);
		System.out.println(result);
	}

}
