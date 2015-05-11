package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class LoginServiceValidations {

	
	public static ArrayList<String> validateLoginService(RemoteException e ) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Remote service can not be reached.");
		return errors;
	}
	
	public static ArrayList<String> validateLogoutService(RemoteException e ) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Remote service can not be reached.");
		return errors;
	}
	
}
