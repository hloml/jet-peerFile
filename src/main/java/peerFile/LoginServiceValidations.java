package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

/**
 * Validace parametrů a zachycení všech možných chyb, které mohou nastat při přihlášení a odhlášení.
 * 
 * @author
 *
 */
public class LoginServiceValidations {

	private final static Logger logger = Logger.getLogger(LoginServiceValidations.class);
	
	/**
	 * 
	 * 
	 * @param e Vyjímka.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateLoginService(RemoteException e ) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Remote service can not be reached.");
		logger.error("Remote service can not be reached.");
		return errors;
	}
	
	/**
	 * 
	 * 
	 * @param e Vyjímka.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateLoginService(AxisFault e, String username ) {
		ArrayList<String> errors = new ArrayList<String>();
		
		if (e.getLocalizedMessage().toLowerCase().contains("bad user/password.")) {	
			errors.add("Username or password is wrong, please try it again.");
			logger.error("Wrong username or password: " + username);
		}
		else if (e.getLocalizedMessage().toLowerCase().contains("service stub initialization failed")){
			errors.add("Service can not be reached");
			logger.error("Service can not be reached for user " + username + " because " + e.getLocalizedMessage());
		}		
		else {
			errors.add("Service can not be reached");
			logger.error("Service can not be reached for user " + username + " on server " + e.getLocalizedMessage());
		}
		
		return errors;
	}
	
	/**
	 * 
	 * 
	 * @param e Vyjímka.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateLogoutService(RemoteException e ) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Remote service can not be reached.");
		logger.error("Remote service can not be reached.");
		return errors;
	}
	
}
