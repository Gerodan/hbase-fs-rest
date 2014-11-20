package cn.gerodan.webREST;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.commons.httpclient.HttpStatus;
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
        return JSON.toJSONString("Server is deleteing..........."+fileID);
    }

    @Path("files/{fileID}")
    @GET
    @Produces("application/json")
    //查找
    public String getFile(@PathParam("fileID") String fileID) {
        return JSON.toJSONString("Server is Getting..........."+fileID);
    }
}
