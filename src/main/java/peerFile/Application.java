package peerFile;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceConfiguration.class);

		ServiceClient serviceClient = ctx.getBean(ServiceClient.class);
		
		serviceClient.call();
	}

}