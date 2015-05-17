package peerFile;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

/**
 * @author Wajzy
 *
 */
public interface LoginService {

	/**
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	public String logout(HttpSession session, Model model);
	
	/**
	 * 
	 * @param session
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(HttpSession session, Model model, String username, String password);
}
