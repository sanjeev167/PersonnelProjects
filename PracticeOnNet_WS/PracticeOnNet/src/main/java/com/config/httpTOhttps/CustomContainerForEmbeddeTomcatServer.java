/**
 * 
 */
package com.config.httpTOhttps;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author Sanjeev
 *
 */
//@Component
public class CustomContainerForEmbeddeTomcatServer
		implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		factory.setContextPath("");
		factory.setPort(8080);
		Ssl ssl = new Ssl();
		factory.setSsl(ssl);
	}
}