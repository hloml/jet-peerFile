package peerFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;


public class FileServiceValidations {


	public static ArrayList<String> validateBrowse(String fileCode) {
		ArrayList<String> errors = new ArrayList<String>();

		if (fileCode == null) {
			errors.add("Missing file code.");
		}
		return errors;
	}

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

	public static ArrayList<String> validateDownload(String fileCode, String fileName) {
		ArrayList<String> errors = new ArrayList<String>();

		if (fileCode == null) {
			errors.add("Missing file code.");
		}
		if (fileName == null) {
			errors.add("Missing file name.");
		}

		return errors;
	}

	public static ArrayList<String> validateDownloadService(RemoteException e,  HttpServletResponse response, String parentCode ) {
		ArrayList<String> errors = new ArrayList<String>();

		if (e.getLocalizedMessage().toLowerCase().contains("entity has no content")) {
			errors.add("File does not have content.");
		} else {
			errors.add("Remote service can not be reached.");
		}
		redirect(response, "/browse?fileCode=" + parentCode);
		return errors;
	}
	
	public static ArrayList<String> validateIndexService(RemoteException e ) {
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Remote service can not be reached.");
		return errors;
	}
	
	
	
	
	private static void redirect(HttpServletResponse response, String address) {
		try {
			response.sendRedirect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
