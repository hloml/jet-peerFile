package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Validace parametrů a zachycení všech možných chyb, které mohou nastat při přihlášení a odhlášení.
 * 
 * @author
 *
 */
public class LoginServiceValidations {

	
	/**
	 * 
	 * 
	 * @param e Vyjímka.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateLoginService(RemoteException e ) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Remote service can not be reached.");
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
		return errors;
	}
	
}
