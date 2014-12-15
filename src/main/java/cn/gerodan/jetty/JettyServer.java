package cn.gerodan.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gerodan.webREST.RESTServicesList;
import cn.gerodan.webREST.WebServiceServlet;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class JettyServer {
	private static final Logger log = LoggerFactory.getLogger(JettyServer.class);
	static Server server = new Server(8080);
	
	/*
	 * 启动Jetty的Web服务器功能
	 */
	private static void startJettyWebServer() throws Exception{
		Server server = new Server(8080);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("/home");
		
		server.setHandler(resourceHandler);
		resourceHandler.setDirectoriesListed(true);
		
		server.start();
		
		log.info("Jetty Start Up Successfully :D");
	}
	/*
	 * 启动Jetty的WebServlet服务器功能
	 */
	private static void startJettyWebServletServer() throws Exception{
		Server server = new Server(8080);
		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping(WebServiceServlet.class, "/web");
		server.start();
		server.join();
		
		log.info("Jetty Start Up Successfully :D");
	}
	
	/*
	 *  关闭Jetty的WebREST服务器功能
	 */
	public static void stopJettyWebRESTServer() throws Exception{
		server.stop();
		
		log.info("Jetty Stop :D");
	}
	/*
	 * 启动Jetty的WebREST服务器功能
	 */
	public static void startJettyWebRESTServer() throws Exception{
	    server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        ServletHolder h = new ServletHolder(new HttpServletDispatcher());
        h.setInitParameter("javax.ws.rs.Application",RESTServicesList.class.getName());
        context.addServlet(h, "/*");
        server.setHandler(context);
        try {
        	if(!server.isStarted()&&!server.isStarting()){
        		server.start();
                server.join();
        	}
        	else{
        		log.info("Jetty is already Started Up :D");
        	}
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		log.info("Jetty Start Up Successfully :D");
	}
	
	

	public static void main(String[] args) throws Exception
	{
		startJettyWebRESTServer();
	}
}
