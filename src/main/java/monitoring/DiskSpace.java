package monitoring;

/**
 * Container for disk information from json monitoring API
 * @author Jara
 *
 */
public class DiskSpace {

	private String driveName;
	private Long totalSpace;
	private Long freeSpace;
	private Long usedSpace;
	
	public String getDriveName() {
		return driveName;
	}
	public void setDriveName(String driveName) {
		this.driveName = driveName;
	}
	public Long getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(Long totalSpace) {
		this.totalSpace = totalSpace;
	}
	public Long getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(Long freeSpace) {
		this.freeSpace = freeSpace;
	}
	public Long getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(Long usedSpace) {
		this.usedSpace = usedSpace;
	}
	
	
	
}
