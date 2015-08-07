package monitoring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import tools.Formatter;

/**
 * Gets monitoring informations from json monitoring API
 * @author Jara
 *
 */
public class JsonMonitoring extends Thread {
	
	private final static Logger logger = Logger.getLogger(JsonMonitoring.class);
	
	private final String monitorApiUrl;

	private static final String PF_VERSION = "pf_version";
	
	private static final String INSTANCE_ID = "instance_id";
	
	private static final String SYSTEM_LOAD = "system_load";
	
	private static final String SESSIONS_COUNT = "sessions_count";
	
	private static final String SESSIONS_INFO = "sessions_info";
		private static final String SESSIONS_CODE = "session_code";
		private static final String SESSIONS_NAME = "session_name";
		private static final String SESSIONS_START = "session_start";
		private static final String SESSIONS_LAST_REQUEST = "last_request";
		private static final String SESSIONS_USERNAME = "user_name";
	
	private static final String MEMORY_INFO = "memory_info";
		private static final String MEMORY_TOTAL = "mem_total";
		private static final String MEMORY_FREE = "mem_free";
		
	private static final String DISK_SPACE = "disk_space";
		private static final String DISK_NAME = "drive_name";
		private static final String DISK_TOTAL = "total_space";
		private static final String DISK_FREE = "free_space";
		
	private static final String PASSENGER_INFO = "passenger_info";
		private static final String PASSENGER_INFO_TEXT = "info_text";
		
	private static final String REPLICATION_CONFIG = "replication_config";
		private static final String REPL_USE = "use_replica";
		private static final String REPL_ACT = "acts_as_replica";
		
	private static final String REPLICA_MASTER_INFO = "replica_master_info";
		private static final String REPLICA_MASTER_WAITING_TO_SEND = "message_count_waiting_to_send";
		private static final String REPLICA_MASTER_WAITING_TO_CONFIRM = "message_count_waiting_for_confirmation";
		private static final String REPLICA_MASTER_FAILED = "message_count_failed";
		
	private static final String REPLICA_SLAVE_INFO = "replica_slave_info";
		private static final String REPLICA_SLAVE_INCOMPLETE = "incomplete_message_count";
		private static final String REPLICA_SLAVE_UNPROCESSED = "unprocessed_message_count";
		private static final String REPLICA_SLAVE_FAILED = "failed_message_count";
	
	private String pf_version = null;
	private String instance_id;
	private Double system_load;
	private Long sessions_count;
	private SessionInfo[] sessions_info;
	private Long[] memory_info;
	private DiskSpace[] disk_space;
	private String passenger_info;
	private Boolean[] replication_config;
	private Long[] replica_master_info;
	private Long[] replica_slave_info;

	/**
	 * 
	 * @param url - address of server (without port and folder)
	 * @param port - port of server
	 */
	public JsonMonitoring(String url, int port) {
		monitorApiUrl = Formatter.getMonitoringApiUrl(url, port);
		this.start();
	}
	
	/**
	 * 
	 * @return peerfile version
	 */
	public String getPf_version() {
		return pf_version;
	}

	/**
	 * 
	 * @return instance ID
	 */
	public String getInstance_id() {
		return instance_id;
	}

	/**
	 * 
	 * @return system load
	 */
	public Double getSystem_load() {
		return system_load;
	}

	/**
	 * 
	 * @return sessions count
	 */
	public Long getSessions_count() {
		return sessions_count;
	}

	/**
	 * 
	 * @return sessions info
	 */
	public SessionInfo[] getSessions_info() {
		return sessions_info;
	}

	/**
	 * 
	 * @return memory info - [0]: total memory, [1]: free memory, [2]: used memory
	 */
	public Long[] getMemory_info() {
		return memory_info;
	}

	/**
	 * 
	 * @return disks space info 
	 */
	public DiskSpace[] getDisk_space() {
		return disk_space;
	}

	/**
	 * 
	 * @return passenger info
	 */
	public String getPassenger_info() {
		return passenger_info;
	}

	/**
	 * 
	 * @return replication config - [0]: use replice, [1]: act as replica
	 */
	public Boolean[] getReplication_config() {
		return replication_config;
	}

	/**
	 * 
	 * @return replica master info - [0]: incomplete messages, [1]: unprocessed messages, [2]: failed messages 
	 */
	public Long[] getReplica_master_info() {
		return replica_master_info;
	}

	/**
	 * 
	 * @return replica slave info - [0]: incomplete messages, [1]: unprocessed messages, [2]: failed messages 
	 */
	public Long[] getReplica_slave_info() {
		return replica_slave_info;
	}
	
	private JsonElement getJsonElement(String method) throws IOException{
		String sURL = monitorApiUrl + method;
		URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.setConnectTimeout(4000);
	    request.connect();

	    // Convert to a JSON object to print data
	    JsonParser jp = new JsonParser(); //from gson
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    return root;
	}

	@Override
	public void run() {
		while(true){
			try {
				// PF_VERSION
				pf_version = getJsonElement(PF_VERSION).getAsJsonObject().get(PF_VERSION).getAsString();

				// INSTANCE_ID
				instance_id = getJsonElement(INSTANCE_ID).getAsJsonObject().get(INSTANCE_ID).getAsString();

				// SYSTEM_LOAD
				system_load = getJsonElement(SYSTEM_LOAD).getAsJsonObject().get(SYSTEM_LOAD).getAsDouble();

				// SESSIONS_COUNT
				sessions_count = getJsonElement(SESSIONS_COUNT).getAsJsonObject().get(SESSIONS_COUNT).getAsLong();

				// SESSIONS_INFO
				JsonArray ja = getJsonElement(SESSIONS_INFO).getAsJsonArray();
				int size = ja.size();
				sessions_info = new SessionInfo[size];
				int i = 0;
				for (JsonElement jsonElement : ja) {
					sessions_info[i] = new SessionInfo();
					sessions_info[i].setSessionCode(jsonElement.getAsJsonObject().get(SESSIONS_CODE).getAsString());
					sessions_info[i].setSessionName(jsonElement.getAsJsonObject().get(SESSIONS_NAME).getAsString());
					sessions_info[i].setSessionStart(Formatter.stringToDate(jsonElement.getAsJsonObject().get(SESSIONS_START).getAsString()));
					sessions_info[i].setLastRequest(Formatter.stringToDate(jsonElement.getAsJsonObject().get(SESSIONS_LAST_REQUEST).getAsString()));
					sessions_info[i].setUserName(jsonElement.getAsJsonObject().get(SESSIONS_USERNAME).getAsString());
					i++;
				}
				
				// MEMORY_INFO
				JsonObject jo = getJsonElement(MEMORY_INFO).getAsJsonObject();
				memory_info = new Long[3];
				memory_info[0] = jo.get(MEMORY_TOTAL).getAsLong();
				memory_info[1] = jo.get(MEMORY_FREE).getAsLong();
				memory_info[2] = memory_info[0] - memory_info[1];
				
				// DISK_SPACE
				ja = null;
				ja = getJsonElement(DISK_SPACE).getAsJsonArray();
				size = ja.size();
				disk_space = new DiskSpace[size];
				i = 0;
				for (JsonElement jsonElement : ja) {
					disk_space[i] = new DiskSpace();
					disk_space[i].setDriveName(jsonElement.getAsJsonObject().get(DISK_NAME).getAsString());
					disk_space[i].setTotalSpace(jsonElement.getAsJsonObject().get(DISK_TOTAL).getAsLong());
					disk_space[i].setFreeSpace(jsonElement.getAsJsonObject().get(DISK_FREE).getAsLong());
					disk_space[i].setUsedSpace(disk_space[i].getTotalSpace() - disk_space[i].getFreeSpace());
					i++;
				}

				// PASSENGER_INFO
				passenger_info = getJsonElement(PASSENGER_INFO).getAsJsonObject().get(PASSENGER_INFO_TEXT).getAsString();
				//passenger_info = Formatter.plainTextToHtml(passenger_info);
				
				// REPLICATION_CONFIG
				jo = null;
				jo = getJsonElement(REPLICATION_CONFIG).getAsJsonObject();
				replication_config = new Boolean[2];
				replication_config[0] = jo.get(REPL_USE).getAsBoolean();
				replication_config[1] = jo.get(REPL_ACT).getAsBoolean();
				
				// REPLICA_MASTER_INFO
				jo = null;
				jo = getJsonElement(REPLICA_MASTER_INFO).getAsJsonObject();
				replica_master_info = new Long[3];
				replica_master_info[0] = jo.get(REPLICA_MASTER_WAITING_TO_SEND).getAsLong();
				replica_master_info[1] = jo.get(REPLICA_MASTER_WAITING_TO_CONFIRM).getAsLong();
				replica_master_info[2] = jo.get(REPLICA_MASTER_FAILED).getAsLong();
			
				// REPLICA_SLAVE_INFO
				jo = null;
				jo = getJsonElement(REPLICA_SLAVE_INFO).getAsJsonObject();
				replica_slave_info = new Long[3];
				replica_slave_info[0] = jo.get(REPLICA_SLAVE_INCOMPLETE).getAsLong();
				replica_slave_info[1] = jo.get(REPLICA_SLAVE_UNPROCESSED).getAsLong();
				replica_slave_info[2] = jo.get(REPLICA_SLAVE_FAILED).getAsLong();
			} catch (IOException e) {
				instance_id = null;
				logger.error("Monitoring instance is not available: " + monitorApiUrl);
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("Thread sleep InterruptedException: " + monitorApiUrl);
			}

		}
	}
}