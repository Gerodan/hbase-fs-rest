package cn.gerodan.webREST;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.lychee.fs.hbase.HBaseFile;
import org.lychee.fs.hbase.HBaseFileResultAdapter;
import org.lychee.fs.hbase.HBaseFileSystem;
import org.lychee.fs.hbase.HBaseFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@Path("/v1/fs")
public class HBaseFSService {
    private static final Logger log = LoggerFactory.getLogger(HBaseFSService.class);

    @Path("files/")
    @POST
    @Produces("application/json")
    //新增
    public String createFile(@FormParam("fileID") String fileID,@FormParam("desc") String desc) {
    	log.info("............"+fileID);
    	log.info("............"+desc);
        return JSON.toJSONString("Server is Createing.."+fileID);
    }

    @Path("files/{fileID}")
    @HEAD
    @Produces("application/json")
    //是否存在
    public int headFile(@PathParam("fileID") String fileID) {
    	return  HttpStatus.SC_OK;
    }
    
    @Path("files/{fileID}")
    @DELETE
    @Produces("application/json")
    //删除
    public String deleteFile(@PathParam("fileID") String fileID) {
    	//待删除文件的MD5
    	String needDeleteFileMD5=fileID;
    	HBaseFile needDeleteFile=HBaseFile.Factory.buildHBaseFile(needDeleteFileMD5);
    
    	if (needDeleteFile.exists()){
    		try {
				needDeleteFile.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	log.info(needDeleteFile.getDesc()+"(MD5:"+needDeleteFile.getIdentifier()+")在HBase上删除成功");
    	}
        return JSON.toJSONString("Server is deleteing..........."+fileID);
    }

    @Path("files/{fileID}")
    @GET
    @Produces("application/json")
    //查找
    public String getFile(@PathParam("fileID") String fileID) {
    	if(StringUtils.isBlank(fileID)){
    	   HBaseFileSystem hbfs=HBaseFileSystem.instance();
		   HBaseFileResultAdapter scanAdapter = hbfs.scan();
	       HBaseFile hbFile;
	       log.info("已经存储的HBaseFile列表");
	       Integer count=0;
	       while ((hbFile = scanAdapter.nextOne()) != null) {
	    		log.info("文件("+(++count)+")MD5："+hbFile.toString());
	       }
	       log.info("共"+count+"个文件");
	       return JSON.toJSONString("共"+count+"个文件");
    	}
    	String needDeleteFileMD5=fileID;
    	HBaseFile findFile=HBaseFile.Factory.buildHBaseFile(needDeleteFileMD5);
        return JSON.toJSONString(findFile);
    }
}
