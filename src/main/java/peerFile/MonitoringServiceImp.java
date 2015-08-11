package peerFile;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import configuration.Server;

/**
 * Implementace monitorovacích služeb.
 * @author Jara
 *
 */
@Service
public class MonitoringServiceImp implements MonitoringService {

	@Override
	public String monitor(HttpSession session, Model model, String serverKey, String autoRefresh, Map<Object, Server> servers) {
		ArrayList<Server> serversArray = new ArrayList<Server>(servers.values());
		model.addAttribute("serversList", serversArray);
		if(serverKey != null){
			if(servers.containsKey(serverKey)) {
				Server s = (Server) servers.get(serverKey);
				model.addAttribute("monitoredServer", s);
			} else {
				model.addAttribute("monitoredServer", null);
			}
		}
		model.addAttribute("autoRefreshTimer", tryParseInt(autoRefresh));
		
		return "monitoring";
	}
	
	private int tryParseInt(String s) {
        int retValue = 0;
        if(s == null) {
            return retValue;
        }
	    try {
	        retValue = Integer.parseInt(s);
	    } catch (NumberFormatException e) {
	        // return 0;
	    }
	    return retValue;
	}
}
