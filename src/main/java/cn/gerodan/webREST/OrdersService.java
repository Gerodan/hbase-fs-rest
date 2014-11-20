package cn.gerodan.webREST;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.alibaba.fastjson.JSON;

@Path("/orders")
public class OrdersService {
    private static Map<String, String> orders = new TreeMap<String, String>();

    @Path("/{order}")
    @PUT
    @Produces("application/json")
    public String create(@PathParam("order") String order,@QueryParam("value") String customerName) {
        orders.put(order, customerName);
        Map<String,String> infoMap=new HashMap<String,String>();
        infoMap.put("info", "OK");
        return  JSON.toJSONString(infoMap);
    }

    @Path("/one/{key}")
    @GET
    @Produces("application/json")
    public String one(@PathParam("key") String key) {
        if (orders.containsKey(key)){
        	 return JSON.toJSONString(orders.get(key));
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @Path("/listAll")
    @GET
    @Produces("application/json")
    public String listAll() {
        return JSON.toJSONString(orders);
    }
}
