package peerFile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ServiceConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("peerFile.wsdl");
		return marshaller;
	}

	@Bean
	public ServiceClient weatherClient(Jaxb2Marshaller marshaller) {
		ServiceClient client = new ServiceClient();
		client.setDefaultUri("http://peerfile.eu:4000/soap/wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}