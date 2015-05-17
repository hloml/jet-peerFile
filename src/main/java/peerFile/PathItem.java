package peerFile;

/**
 * Třída uchovávající jméno a kód souboru/složky.
 * 
 * @author
 *
 */
public class PathItem {
	private String fileName;
	private String fileCode;
	
	/**
	 * Konstruktor.
	 * 
	 * @param fileName Jméno souboru.
	 * @param fileCode Kód souboru.
	 */
	public PathItem(String fileName, String fileCode) {
		setFileCode(fileCode);
		setFileName(fileName);
	}
	
	/**
	 * Získá jméno souboru.
	 * 
	 * @return Jméno souboru.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Nastaví jméno souboru.
	 * 
	 * @param fileName Jméno souboru.
	 */
	public void setFileName(String fileName) {
		if (fileName.trim().isEmpty()) {
			this.fileName = "";
		}
		else {
			this.fileName = fileName;
		}
	}
	/**
	 * Získá kód souboru/složky.
	 * 
	 * @return Kód souboru.
	 */
	public String getFileCode() {
		return fileCode;
	}
	/**
	 * Nastaví kód souboru/složky.
	 * 
	 * @param fileCode Kód souboru.
	 */
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

		
}
