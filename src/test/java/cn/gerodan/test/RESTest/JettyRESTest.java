package cn.gerodan.test.RESTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lychee.fs.hbase.HBaseFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.gerodan.jetty.JettyServer;
import cn.gerodan.util.HttpClientUtil;
import cn.gerodan.webREST.HBaseFSService;

public class JettyRESTest {
    private static final Logger log = LoggerFactory.getLogger(JettyRESTest.class);
	
	private static final String uploadPath = "/home/gerodan/SCWork/JettyREST/hbase-fs-rest/filesIn";
    private static final String outPath = uploadPath + File.separator + "filesOut" + File.separator;

	private static HttpClientUtil client = null;

	@BeforeClass
	public static void setUp() throws Exception {
		client = new HttpClientUtil();
		FileUtils.deleteDirectory(new File(outPath));
		FileUtils.forceMkdir(new File(outPath));
		log.info("清空并重建"+outPath+"");

		//启动Jetty
		JettyServer.startJettyWebRESTServer();
	}
	
	@BeforeClass
	public static void afterClass() throws Exception {
    	//关闭Jetty
    	//JettyServer.stopJettyWebRESTServer();
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
		String key="";
		String url="http://localhost:8080/v1/fs/files/"+key;
		String result = client.doGet(url);
		System.out.println(result);
	}
	
	@Test
	public void restDelete() {
		final File uploadFolder = new File(uploadPath);
    	Collection<File> uploadFiles = FileUtils.listFiles(uploadFolder, null, false);
    	for(File thisFile:uploadFiles){
    		//文件原MD5
    		try {
				String originalMD5=md5Hex(thisFile);
				String filleID=originalMD5;
				//添加请求(Put)
				String url="http://localhost:8080/v1/fs/files/"+filleID;
				String result = client.doDelete(url);
				System.out.println(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	log.info("删除测试结束!!!!!!!!!!!!!!!");
		
	}
	
	public static String md5Hex(File file) throws IOException {
		String md5;
		try (InputStream is = new FileInputStream(file)) {
			md5 = DigestUtils.md5Hex(is);
		}
		return md5;
	}

}
