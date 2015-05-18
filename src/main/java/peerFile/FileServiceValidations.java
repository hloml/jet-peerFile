package peerFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * Validace parametrů a zachycení všech možných chyb v operacích se soubory/složkami. 
 * 
 * @author Wajzy
 *
 */
public class FileServiceValidations {

	private final static Logger logger = Logger.getLogger(FileServiceValidations.class);
	
	/**
	 * Ověření parametru fileCode, který musí mít nějakou hodnotu.
	 * 
	 * @param fileCode Kód souboru/složky
	 * @return Seznam chyb
	 */
	public static ArrayList<String> validateBrowse(String fileCode) {
		ArrayList<String> errors = new ArrayList<String>();

		if (fileCode == null) {
			errors.add("Missing file code.");
			logger.error("Missing file code.");
		}
		return errors;
	}

	/**
	 * Ověření jestli uživatel má přístup do složky a jestli složka existuje.  
	 * 
	 * @param e Vyjímka.
	 * @param response Http odpověď.
	 * @param path Cesta v souborovém systému.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateBrowseService(RemoteException e,  HttpServletResponse response,ArrayList<PathItem> path ) {
		ArrayList<String> errors = new ArrayList<String>();

		if (e.getLocalizedMessage().toLowerCase().contains("insufficient rights")) {
			errors.add("You don't have permission to access this folder.");
			if (path != null && path.size() - 2 >= 0) {
				redirect(response, "/browse?fileCode=" + path.get(path.size() - 2).getFileCode());
			} else {
				redirect(response, "/index");
			}
		} else if (e.getLocalizedMessage().toLowerCase().contains("folder not found")) {
			errors.add("Folder not found");
			redirect(response, "/index");
		} else {
			errors.add("Remote service can not be reached.");
			redirect(response, "/index");
		}
		return errors;
	}

	/**
	 * Ověření parametrů fileCode a fileName, které oba musí mít nějakou hodnotu.
	 * 
	 * @param fileCode Kód souboru/složky.
	 * @param fileName Jméno Kód souboru/složky.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateDownload(String fileCode, String fileName) {
		ArrayList<String> errors = new ArrayList<String>();

		if (fileCode == null) {
			errors.add("Missing file code.");
			logger.error("Missing file code.");
		}
		if (fileName == null) {
			errors.add("Missing file name.");
			logger.error("Missing file name.");
		}

		return errors;
	}

	/**
	 * 
	 * 
	 * @param e Vyjímka.
	 * @param response Http odpověď.
	 * @param parentCode Kód nadřazeného souboru/složky.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateDownloadService(RemoteException e,  HttpServletResponse response, String parentCode ) {
		ArrayList<String> errors = new ArrayList<String>();

		if (e.getLocalizedMessage().toLowerCase().contains("entity has no content")) {
			errors.add("File does not have content.");
			logger.error("File does not have content.");
		} else {
			errors.add("Remote service can not be reached.");
			logger.error("Remote service can not be reached.");
		}
		redirect(response, "/browse?fileCode=" + parentCode);
		return errors;
	}
	
	/**
	 * 
	 * 
	 * @param e Vyjímka.
	 * @return Seznam chyb.
	 */
	public static ArrayList<String> validateIndexService(RemoteException e ) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Remote service can not be reached.");
		logger.error("Remote service can not be reached.");
		return errors;
	}
		
	
	/**
	 * Zachycuje chybu při přesměrování.
	 * 
	 * @param response Http odpověď.
	 * @param address Adresa přesměrování.
	 */
	private static void redirect(HttpServletResponse response, String address) {
		try {
			response.sendRedirect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
