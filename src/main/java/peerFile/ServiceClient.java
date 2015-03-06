package peerFile;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import peerFile.wsdl.*;

public class ServiceClient extends WebServiceGatewaySupport {

	public void call() {
		ObjectFactory factory = new ObjectFactory();

		getWebServiceTemplate().marshalSendAndReceive(
				factory.createCreateAttributes(),
				new SoapActionCallback("http://peerfile.eu:4000/soap/action"));

	}

}