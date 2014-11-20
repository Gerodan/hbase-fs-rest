package cn.gerodan.webREST;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebServiceServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(WebServiceServlet.class);

    private static final long serialVersionUID = 1L;
    public WebServiceServlet() {
    }
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		log.info("＃＃＃＃＃＃＃￥￥￥￥￥￥doGet");
    }
    
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		log.info("doPost");
    }
}
