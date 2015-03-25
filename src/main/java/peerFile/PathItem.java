package peerFile;

public class PathItem {
	private String fileName;
	private String fileCode;
	
	public PathItem(String fileName, String fileCode) {
		this.fileCode = fileCode;
		this.fileName = fileName;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

		
}
