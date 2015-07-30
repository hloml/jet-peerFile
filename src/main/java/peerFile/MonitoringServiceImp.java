package peerFile;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import configuration.Server;
import monitoring.JsonMonitoring;

/**
 * Implementace monitorovacích služeb.
 * @author Jara
 *
 */
@Service
public class MonitoringServiceImp implements MonitoringService {

	@Override
	public String monitor(HttpSession session, Model model, String serverKey, Map<Object, Object> servers) {
		if(serverKey != null){
			if(servers.containsKey(serverKey)){
				Server s = (Server) servers.get(serverKey);
				JsonMonitoring jsm = new JsonMonitoring(s.getAddress(), s.getPort());
				model.addAttribute("serverMonitor", jsm);
				model.addAttribute("choosenServer", s);
			} else {
				model.addAttribute("serverMonitor", null);
			}
		}
		return "monitoring";
	}
	
}
