package monitoring;

import java.util.Date;

/**
 * Container for session info from json monitoring API
 * @author Jara
 *
 */
public class SessionInfo {

	private String sessionCode;
	private String sessionName;
	private Date sessionStart;
	private Date lastRequest;
	private String userName;
	
	
	public String getSessionCode() {
		return sessionCode;
	}
	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public Date getSessionStart() {
		return sessionStart;
	}
	public void setSessionStart(Date sessionStart) {
		this.sessionStart = sessionStart;
	}
	public Date getLastRequest() {
		return lastRequest;
	}
	public void setLastRequest(Date lastRequest) {
		this.lastRequest = lastRequest;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
