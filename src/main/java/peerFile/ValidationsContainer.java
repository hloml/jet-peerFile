package peerFile;

import java.util.ArrayList;

public class ValidationsContainer {

	private ArrayList<String> errors;
	private String url;
	
	public ValidationsContainer(ArrayList<String> errors, String url) {
		this.errors = errors;
		this.url = url;
	}
	
	public ArrayList<String> getErrors() {
		return errors;
	}
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
