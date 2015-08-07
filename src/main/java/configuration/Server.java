package configuration;

import monitoring.JsonMonitoring;

public class Server {

	String code;
	String address;
	String description;
	int port;
	private JsonMonitoring monitoring;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public JsonMonitoring monitoring() {
		if(monitoring == null) {
			monitoring = new JsonMonitoring(address, port);
		}
		return monitoring;
	}

}