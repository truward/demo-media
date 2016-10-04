package com.truward.demo.media.website;

import com.truward.demo.media.DemoMediaServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author Alexander Shabanov
 */
@ParametersAreNonnullByDefault
public final class DemoMediaLauncher {

  public static void main(String[] args) throws Exception {
    System.setProperty("logback.configurationFile", "logback.xml");

    final int port = getIntPropertyDefault("demo.website.port", 8080);
    final Server server = new Server(port);

    final ServletContextHandler contextHandler = new ServletContextHandler();
    contextHandler.setContextPath("/");
    contextHandler.addServlet(DemoMediaServlet.class, "/demo/media/*");

    final HandlerCollection handlerList = new HandlerCollection();
    handlerList.addHandler(contextHandler);
    server.setHandler(handlerList);

    server.start();
    server.join();
  }

  private static int getIntPropertyDefault(String name, int defaultValue) {
    final String val = System.getProperty(name, Integer.toString(defaultValue));
    return Integer.parseInt(val);
  }
}
