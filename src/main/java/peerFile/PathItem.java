package peerFile;

public class PathItem {
	private String fileName;
	private String fileCode;
	
	public PathItem(String fileName, String fileCode) {
		setFileCode(fileCode);
		setFileName(fileName);
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		if (fileName.trim().isEmpty()) 
			this.fileName = "";
		else
			this.fileName = fileName;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

		
}
