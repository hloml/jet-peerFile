package peerFile;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

/**
 * Rozhraní logovacích služeb. Přihlášení a odhlášení.
 * 
 * @author
 *
 */
public interface LoginService {

	/**
	 * Odhlášení.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @return Adresa přesměrování.
	 */
	public String logout(HttpSession session, Model model);
	
	/**
	 * Ověření uživatelského jména a hesla a následné přihlášení.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @param username Uživatelské jméno.
	 * @param password Uživatelské heslo.
	 * @return Adresa přesměrování.
	 */
	public String login(HttpSession session, Model model, String username, String password, String server);
}
