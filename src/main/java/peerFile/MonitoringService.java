package peerFile;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import configuration.Server;

/**
 * Rozhraní monitorovacích služeb.
 * @author Jara
 *
 */
public interface MonitoringService {

	/**
	 * Zobrazení monitoringu
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @param serverKey Klíč serveru.
	 * @param servers Mapa serverů.
	 * @return Http odpověď.
	 */
	public String monitor(HttpSession session, Model model, String serverKey, String autoRefresh, Map<Object, Server> servers);
}
