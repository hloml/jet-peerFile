package peerFile;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface LoginService {

	public String logout(HttpSession session, Model model);
	
	public String login(HttpSession session, Model model, String username, String password);
}
