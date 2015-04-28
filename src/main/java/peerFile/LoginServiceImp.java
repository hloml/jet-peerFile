package peerFile;

import java.rmi.RemoteException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginServiceImp implements LoginService {
	
	@Autowired
	private ServiceClient client;

	private final String code = "code";
	private final String error = "errorMessage";
	
	public String logout(HttpSession session, Model model){
		try {
			if (session.getAttribute(code) == null) {
				model.addAttribute("errorMessage", "Access denied. Please log in.");
				return "index.jsp";
			}
			client.logout(session.getAttribute(code).toString());
			session.removeAttribute(code);
			
			return "index.jsp";
		} catch (RemoteException e) {
			model.addAttribute(error, "Remote service can not be reached.");
			e.printStackTrace();
			return "index.jsp";
		}
	}
	
	public String login(HttpSession session, Model model, String username, String password){
		String sessionCode = "";
		
		try {
			if(session.getAttribute(code) == null){
				sessionCode = client.getLogin(username, password);
				if (sessionCode.equals(null) || sessionCode.equals("")) {
					model.addAttribute(error,
							"Username or password is wrong, please try it again.");
					return "index.jsp";
				}
				session.setAttribute("code", sessionCode);
			}
			
		} catch (RemoteException e) {
			model.addAttribute(error, "Remote service can not be reached.");
			e.printStackTrace();
			return "index.jsp";
		}
		
		return "/index";
	}
}
