package br.com.sysmi.labproject;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("br.com.sysmi.labproject")
@PropertySource("classpath:application.properties")
public class CamelConfiguration {
	
	@Value("${jms.broker.host}")
	private String brokerHost;

	@Value("${jms.broker.user}")
	private String brokerUserName;
	
	@Value("${jms.broker.password}")
	private String brokerPassword;    
	
	public JmsConnectionFactory getJmsConnectionFactory(final String password) {
		final JmsConnectionFactory factory = new JmsConnectionFactory();
		factory.setRemoteURI("amqp://" + brokerHost);
		factory.setUsername(brokerUserName);
		factory.setPassword(password);
		
		return factory;
	}
	
	@Bean(value="jmsConnectionFactory")
	public PooledConnectionFactory getPooledConnectionFactory() {
		final PooledConnectionFactory factory = new PooledConnectionFactory();
		factory.setConnectionFactory(this.getJmsConnectionFactory(brokerPassword));
		factory.setMaxConnections(10);

		return factory;
	}
	
	@Bean
	ServletRegistrationBean servletRegistrationBean() {
	    ServletRegistrationBean servlet = new ServletRegistrationBean
	      (new CamelHttpTransportServlet(), "/*");
	    servlet.setName("CamelServlet");
	    return servlet;
	}
}
